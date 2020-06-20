/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author Obaydah Mohamad
 */
public class DosObject {
    private int count = 0; //Antal gange
    private long time; //Millisekunder
    
    public DosObject(long time){
        this.count++;
        this.time = time;
    }
    
    public void incrementCount(){
        this.count++;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "DosObject{" + "count=" + count + ", time=" + time + '}';
    }
    
    
    
}
