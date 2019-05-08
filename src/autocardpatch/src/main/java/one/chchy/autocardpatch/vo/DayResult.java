package one.chchy.autocardpatch.vo;


import java.util.Date;

public class DayResult {

    private boolean onWork = true;
    private boolean offWork = true;
    private Date workDate = new Date();

    public boolean isOnWork() {
        return onWork;
    }

    public void setOnWork(boolean onWork) {
        this.onWork = onWork;
    }

    public boolean isOffWork() {
        return offWork;
    }

    public void setOffWork(boolean offWork) {
        this.offWork = offWork;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    @Override
    public String toString() {
        return workDate.toString() + ", 上班" + (onWork ? "正常" : "异常") + ", 下班" + (offWork ? "正常" : "异常");
    }
}
