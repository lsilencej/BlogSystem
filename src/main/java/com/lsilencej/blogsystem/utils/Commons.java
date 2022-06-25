package com.lsilencej.blogsystem.utils;

import com.lsilencej.blogsystem.entity.Article;
import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

 @Component
public class Commons {

     // 网站链接
    public static String site_url() {
        return site_url("/page/1");
    }

    // 返回全地址
    public static String site_url(String sub) {
        return site_option("site_url") + sub;
    }

    public static String site_option(String key) {
        return site_option(key, "");
    }

    public static String site_option(String key, String defalutValue) {
        if (StringUtils.isBlank(key)) {
            return "";
        }
        return defalutValue;
    }

    // 截取字符串
    public static String substr(String str, int len) {
        if (str.length() > len) {
            return str.substring(0, len);
        }
        return str;
    }

     // 返回日期
     public static String dateFormat(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
     }

    // 返回文章链接地址
    public static String permalink(Integer articleId) {
        return site_url("/article/" + articleId.toString());
    }

    // 截取文章摘要
    public static String intro(Article article, int len) {
        String value = article.getContent();
        int pos = value.indexOf("<!--more-->");
        if (pos != -1) {
            String html = value.substring(0, pos);
            return MyUtils.htmlToText(MyUtils.mdToHtml(html));
        } else {
            String text = MyUtils.htmlToText(MyUtils.mdToHtml(value));
            if (text.length() > len) {
                return text.substring(0, len)+"......";
            }
            return text;
        }
    }

    // 将 Markdown 转换为 Html
    public static String article(String value) {
        if (StringUtils.isNotBlank(value)) {
            value = value.replace("<!--more-->", "\r\n");
            return MyUtils.mdToHtml(value);
        }
        return "";
    }

    // 文章缩略图
    public static String show_thumb(Article article) {
        if (StringUtils.isNotBlank(article.getThumbnail())){
            return article.getThumbnail();
        }
        return "https://image.lsilencej.top/i/2022/05/07/62764046d8c1d.jpg";
    }

    // 转换为 Emoji
    public static String emoji(String value) {
        return EmojiParser.parseToUnicode(value);
    }

}
