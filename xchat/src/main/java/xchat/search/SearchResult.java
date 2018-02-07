package xchat.search;

/**
 * Created by :Guozhihua
 * Date： 2018/2/7.
 */
public class SearchResult {

    private boolean isSearched;

    private String rightAnswer;

    private int count;

    public SearchResult(boolean isSearched, String rightAnswer) {
        this.isSearched = isSearched;
        this.rightAnswer = rightAnswer;
    }
    public boolean isSearched() {
        return isSearched;
    }
    public void setSearched(boolean searched) {
        isSearched = searched;
    }
    public String getRightAnswer() {
        return rightAnswer;
    }
    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
