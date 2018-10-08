package com.huayu.irla.privilege.manage.service;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;
import com.huayu.irla.privilege.manage.common.PrivilegeTool;
import com.huayu.irla.privilege.manage.dao.ISysUsersRepositoryDao;
import com.huayu.irla.privilege.manage.vo.SysPersistentLoginsVO;


public class IEALPersistentTokenRememberMeServices extends AbstractRememberMeServices {
	private SecureRandom random;
	
	/**
	 * 设置记住密码开关是打开的
	 */
	private boolean alwaysRemember;
	
	@Autowired
	private ISysUsersRepositoryDao sysUsersRepositoryDao;

	public static final int DEFAULT_SERIES_LENGTH = 16;
	public static final int DEFAULT_TOKEN_LENGTH = 16;

	private int seriesLength = DEFAULT_SERIES_LENGTH;
	private int tokenLength = DEFAULT_TOKEN_LENGTH;

	public IEALPersistentTokenRememberMeServices(String key,
			UserDetailsService userDetailsService) {
		super(key, userDetailsService);
		random = new SecureRandom();
	}
	
	/**
	 * Allows customization of whether a remember-me login has been requested. The default
	 * is to return true if <tt>alwaysRemember</tt> is set or the configured parameter
	 * name has been included in the request and is set to the value "true".
	 *
	 * @param request the request submitted from an interactive login, which may include
	 * additional information indicating that a persistent login is desired.
	 * @param parameter the configured remember-me parameter name.
	 *
	 * @return true if the request includes information indicating that a persistent login
	 * has been requested.
	 */
	@Override
	public boolean rememberMeRequested(HttpServletRequest request, String parameter) {
		if (alwaysRemember) {
			return true;
		}

		String paramValue = request.getParameter(parameter);
		if(StringUtils.isBlank(paramValue)) {
			JSONObject obj = PrivilegeTool.getObjJson(request);
			if(null != obj) {
				paramValue = obj.getString(parameter);
			}
		}

		if (paramValue != null) {
			if (paramValue.equalsIgnoreCase("true") || paramValue.equalsIgnoreCase("on")
					|| paramValue.equalsIgnoreCase("yes") || paramValue.equals("1")) {
				return true;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Did not send remember-me cookie (principal did not set parameter '"
					+ parameter + "')");
		}

		return false;
	}

	/**
	 * Locates the presented cookie data in the token repository, using the series id. If
	 * the data compares successfully with that in the persistent store, a new token is
	 * generated and stored with the same series. The corresponding cookie value is set on
	 * the response.
	 *
	 * @param cookieTokens the series and token values
	 *
	 * @throws RememberMeAuthenticationException if there is no stored token corresponding
	 * to the submitted cookie, or if the token in the persistent store has expired.
	 * @throws InvalidCookieException if the cookie doesn't have two tokens as expected.
	 * @throws CookieTheftException if a presented series value is found, but the stored
	 * token is different from the one presented.
	 */
	protected UserDetails processAutoLoginCookie(String[] cookieTokens,
			HttpServletRequest request, HttpServletResponse response) {

		if (cookieTokens.length != 2) {
			throw new InvalidCookieException("Cookie token did not contain " + 2
					+ " tokens, but contained '" + Arrays.asList(cookieTokens) + "'");
		}

		final String presentedSeries = cookieTokens[0];
		final String presentedToken = cookieTokens[1];

		SysPersistentLoginsVO token = sysUsersRepositoryDao.getTokenForSeries(presentedSeries);

		if (token == null) {
			// No series match, so we can't authenticate using this cookie
			throw new RememberMeAuthenticationException(
					"No persistent token found for series id: " + presentedSeries);
		}

		// We have a match for this user/series combination
		if (!presentedToken.equals(token.getToken())) {
			// Token doesn't match series value. Delete all logins for this user and throw
			// an exception to warn them.
			sysUsersRepositoryDao.removeUserTokens(token.getUserCode());

			throw new CookieTheftException(
					messages.getMessage(
							"PersistentTokenBasedRememberMeServices.cookieStolen",
							"Invalid remember-me token (Series/token) mismatch. Implies previous cookie theft attack."));
		}

		if (token.getLastUsed().getTime() + getTokenValiditySeconds() * 1000L < System
				.currentTimeMillis()) {
			throw new RememberMeAuthenticationException("Remember-me login has expired");
		}

		// Token also matches, so login is valid. Update the token value, keeping the
		// *same* series number.
		if (logger.isDebugEnabled()) {
			logger.debug("Refreshing persistent login token for user '"
					+ token.getUserCode()+ "', series '" + token.getSeries() + "'");
		}

		SysPersistentLoginsVO newToken = new SysPersistentLoginsVO(
				token.getUserCode(), token.getSeries(), generateTokenData(), new Date());

		try {
			sysUsersRepositoryDao.updateToken(newToken.getSeries(), newToken.getToken(),
					newToken.getLastUsed());
			addCookie(newToken, request, response);
		}
		catch (Exception e) {
			logger.error("Failed to update token: ", e);
			throw new RememberMeAuthenticationException(
					"Autologin failed due to data access problem");
		}

		return getUserDetailsService().loadUserByUsername(token.getUserCode());
	}

	/**
	 * Creates a new persistent login token with a new series number, stores the data in
	 * the persistent token repository and adds the corresponding cookie to the response.
	 *
	 */
	protected void onLoginSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication successfulAuthentication) {
		String username = successfulAuthentication.getName();

		logger.debug("Creating new persistent login for user " + username);

		SysPersistentLoginsVO persistentToken = new SysPersistentLoginsVO(
				username, generateSeriesData(), generateTokenData(), new Date());
		try {
			sysUsersRepositoryDao.createNewToken(persistentToken);
			addCookie(persistentToken, request, response);
		}
		catch (Exception e) {
			logger.error("Failed to save persistent token ", e);
		}
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
		super.logout(request, response, authentication);

		if (authentication != null) {
			sysUsersRepositoryDao.removeUserTokens(authentication.getName());
		}
	}

	protected String generateSeriesData() {
		byte[] newSeries = new byte[seriesLength];
		random.nextBytes(newSeries);
		return new String(Base64.encode(newSeries));
	}

	protected String generateTokenData() {
		byte[] newToken = new byte[tokenLength];
		random.nextBytes(newToken);
		return new String(Base64.encode(newToken));
	}

	private void addCookie(SysPersistentLoginsVO token, HttpServletRequest request,
			HttpServletResponse response) {
		setCookie(new String[] { token.getSeries(), token.getToken() },
				getTokenValiditySeconds(), request, response);
	}

	public void setSeriesLength(int seriesLength) {
		this.seriesLength = seriesLength;
	}

	public void setTokenLength(int tokenLength) {
		this.tokenLength = tokenLength;
	}

	@Override
	public void setTokenValiditySeconds(int tokenValiditySeconds) {
		Assert.isTrue(tokenValiditySeconds > 0,
				"tokenValiditySeconds must be positive for this implementation");
		super.setTokenValiditySeconds(tokenValiditySeconds);
	}
}
