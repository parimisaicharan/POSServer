/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ISRetail.ArticleDataDownload;

/**
 *
 * @author eyeplus
 */
public class ArticleListPOJO {

    private int SlNo;
    private String MaterialCode;
    private boolean ArticleFlag;
    private boolean UCPFlag;
    private boolean TAXFlag;

    public int getSlNo() {
        return SlNo;
    }

    public void setSlNo(int SlNo) {
        this.SlNo = SlNo;
    }

    public String getMaterialCode() {
        return MaterialCode;
    }

    public void setMaterialCode(String MaterialCode) {
        this.MaterialCode = MaterialCode;
    }

    /**
     * @return the ArticleFlag
     */
    public boolean isArticleFlag() {
        return ArticleFlag;
    }

    /**
     * @param ArticleFlag the ArticleFlag to set
     */
    public void setArticleFlag(boolean ArticleFlag) {
        this.ArticleFlag = ArticleFlag;
    }

    /**
     * @return the UCPFlag
     */
    public boolean isUCPFlag() {
        return UCPFlag;
    }

    /**
     * @param UCPFlag the UCPFlag to set
     */
    public void setUCPFlag(boolean UCPFlag) {
        this.UCPFlag = UCPFlag;
    }

    /**
     * @return the TAXFlag
     */
    public boolean isTAXFlag() {
        return TAXFlag;
    }

    /**
     * @param TAXFlag the TAXFlag to set
     */
    public void setTAXFlag(boolean TAXFlag) {
        this.TAXFlag = TAXFlag;
    }
}
