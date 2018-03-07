package com.newbig.word2xml.utils;

/**
 * User: haibo
 * Date: 2018/1/28 下午2:15
 * Desc:
 */
public class Constants {
    public static final String XREF_BIBR="<xref ref-type=\"bibr\" rid=\"b${id}\">${id}</xref>";
    public static final String XREF_FIG="<xref ref-type=\"fig\" rid=\"Figure${id}\">图 ${name}</xref>\n";
    public static final String FIG="<fig id=\"Figure${id}\">\n" +
            "<label>${id}</label>\n" +
            "<caption>\n" +
            "<p>${figureNameCh}</p>\n" +
            "<p xml:lang=\"en\">${figureNameEn}</p>\n" +
            "</caption>\n" +
            "<graphic xmlns:xlink=\"http://www.w3.org/1999/xlink\" xlink:href=\"${figureLink}\"/>\n" +
            "</fig>\n";
    public static final String FORMULA="<disp-formula>\n" +
            "<label>${id}</label>\n" +
            "<tex-math id=\"E${id}\">\n" +
            "\\begin{document} $$ ${formula} $$ \\end{document}\n" +
            "</tex-math>\n" +
            "</disp-formula>\n" +
            "</p>";

    public static final String SEC_BEGIN="<sec id=\"s${id}\">\n" +
            "<label>${label}</label>\n" +
            "<title>${title}</title>\n" ;
    public static final String SEC_END="\n</sec>";
    public static final String REF_SEPRATOR="========";
    public static final String REF_J="[J].";
    public static final String REF_D="[D].";
    public static final String REF_S="[S].";
    public static final String REF_R="[R].";
}
