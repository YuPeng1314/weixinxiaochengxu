package com.huayu.irla.privilege.manage.service;
	  
import  java.awt.Color;
import  java.awt.Font;
import  java.awt.image.ImageFilter;

import  com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import  com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import  com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.color.RandomRangeColorGenerator;
import  com.octo.captcha.component.image.deformation.ImageDeformation;
import  com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import  com.octo.captcha.component.image.fontgenerator.FontGenerator;
import  com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import  com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import  com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.BaffleTextDecorator;
import  com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import  com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import  com.octo.captcha.component.image.wordtoimage.WordToImage;
import  com.octo.captcha.component.word.FileDictionary;
import  com.octo.captcha.component.word.wordgenerator.ComposeDictionaryWordGenerator;
import  com.octo.captcha.component.word.wordgenerator.WordGenerator;
import  com.octo.captcha.engine.image.ListImageCaptchaEngine;
import  com.octo.captcha.image.gimpy.GimpyFactory;  
  
/**  
 * JCaptcha验证码图片生成引擎, 仿照JCaptcha2.0编写类似GMail验证码的样式.  
 */   
public   class  GMailEngine  extends  ListImageCaptchaEngine {  
  
    @Override   
    protected   void  buildInitialFactories() {  
  
        // 图片和字体大小设置   
        int  minWordLength =  4 ;  
        int  maxWordLength =  5 ;  
        int  fontSize =  20 ;  
        int  imageWidth =  100 ;  
        int  imageHeight =  36 ;  
  
        WordGenerator dictionnaryWords = new  ComposeDictionaryWordGenerator(  
                new  FileDictionary( "toddlist" ));  
        
	     // 背景颜色随机生成  
	     // 验证码的颜色-使用随机颜色器new Integer[]{0,100},new Integer[]{0,100}, new  
	     // Integer[]{0,100}  
	     RandomRangeColorGenerator cgen = new RandomRangeColorGenerator(  
	     new int[] { 150, 255 }, new int[] { 150, 255},  
	     new int[] {  150, 255 });  
  
        //文字干扰器--- 可以创建多个  
       // BaffleTextDecorator baffleTextDecorator = new BaffleTextDecorator(2,cgen);//气泡干扰  
       // LineTextDecorator lineTextDecorator = new LineTextDecorator(1,cgen);//曲线干扰  
        //TextDecorator[] textDecorators = new TextDecorator[1];  
        //textDecorators[0] = baffleTextDecorator;  
        //textDecorators[1] = lineTextDecorator;  
        
        // word2image components   
        TextPaster randomPaster = new  DecoratedRandomTextPaster(minWordLength,  
                maxWordLength, new  RandomListColorGenerator( new  Color[] {  
                        new  Color( 23 ,  170 ,  27 ),  new  Color( 220 ,  34 ,  11 ),  
                        new  Color( 23 ,  67 ,  172 ) }),new  TextDecorator[] {});  
        BackgroundGenerator background = new  UniColorBackgroundGenerator(  
                imageWidth, imageHeight, new Color(206, 195, 179));  
        FontGenerator font = new  RandomFontGenerator(fontSize, fontSize,  
                new  Font[] {  new  Font( "nyala" , Font.BOLD, fontSize),  
                        new  Font( "Bell MT" , Font.PLAIN, fontSize),  
                        new  Font( "Credit valley" , Font.BOLD, fontSize) });  
          
        ImageDeformation postDef = new  ImageDeformationByFilters(  
                new  ImageFilter[] {});  
        ImageDeformation backDef = new  ImageDeformationByFilters(  
                new  ImageFilter[] {});  
        ImageDeformation textDef = new  ImageDeformationByFilters(  
                new  ImageFilter[] {});  
  
        WordToImage word2image = new  DeformedComposedWordToImage(font,  
                background, randomPaster, backDef, textDef, postDef);  
        
      
          
        addFactory(new  GimpyFactory(dictionnaryWords, word2image));  
    }  
}  
