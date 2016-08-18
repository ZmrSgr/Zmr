package cn.sgr.zmr.com.sgr.Modules.Home.Model;




public class EventDatas {

    private String name;
    private String time,text_wuli,text_yao,text_tem;

    public EventDatas(String name, String time) {
        this.name = name;
        this.time = time;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText_wuli() {
        return text_wuli;
    }

    public void setText_wuli(String text_wuli) {
        this.text_wuli = text_wuli;
    }

    public String getText_yao() {
        return text_yao;
    }

    public void setText_yao(String text_yao) {
        this.text_yao = text_yao;
    }

    public String getText_tem() {
        return text_tem;
    }

    public void setText_tem(String text_tem) {
        this.text_tem = text_tem;
    }
}