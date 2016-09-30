package cn.sgr.zmr.com.sgr.Modules.Health.Model;

import java.util.List;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public class SearchResult {
    String piece;
    String img;
     List<Tie> list;

    public String getPiece() {
        return piece;
    }

    public void setPiece(String piece) {
        this.piece = piece;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<Tie> getList() {
        return list;
    }

    public void setList(List<Tie> list) {
        this.list = list;
    }
}
