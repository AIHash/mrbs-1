package com.wafersystems.mrbs.dao.base;

import java.util.List;

public class PageData<T> {   
    
    private List<T> resultlist;   
    private long TotalRecords;   
       
    public List<T> getResultlist() {   
        return resultlist;   
    }   
    public void setResultlist(List<T> resultlist) {   
        this.resultlist = resultlist;   
    }   
    public long getTotalRecords() {   
        return TotalRecords;   
    }   
    public void setTotalRecords(long totalRecords) {   
        TotalRecords = totalRecords;   
    }   
       
}


class PageIndex {   
    
    private long startindex;   
    private long endindex;   
       
    public PageIndex(long startindex, long endindex) {   
        this.startindex = startindex;   
        this.endindex = endindex;   
    }   
    public long getStartindex() {   
        return startindex;   
    }   
    public void setStartindex(long startindex) {   
        this.startindex = startindex;   
    }   
    public long getEndindex() {   
        return endindex;   
    }   
    public void setEndindex(long endindex) {   
        this.endindex = endindex;   
    }   
       
}   
  
  
class PageView<T> {   
    /** 分页数据 **/  
    private List<T> records;   
    /** 页码开始索引和结束索引 **/  
    private PageIndex pageindex;   
    /** 总页数 **/  
    private long totalpage = 1;   
    /** 每页显示记录数 **/  
    private int maxresult = 12;   
    /** 当前页 **/  
    private int currentpage = 1;   
    /** 总记录数 **/  
    private long totalrecord;   
    /** 尾页 **/  
    private int lastpage;   
    /** 页码数量 **/  
    private int pagecode = 15;   
    /** 要获取记录的开始索引 **/  
    public int getFirstResult() {   
        return (this.currentpage-1)*this.maxresult;   
    }   
    /**取得首页**/  
    public int getTopPageNo() {   
        return 1;   
    }   
    /** 取得尾页**/  
    public long getBottomPageNo() {   
        return getTotalpage();   
    }   
       
    /**上一页**/  
    public int getPreviousPageNo() {   
        if (currentpage <= 1) {   
            return 1;   
        }   
        return currentpage - 1;   
    }   
       
    /**下一页* */  
    public long getNextPageNo() {   
        if (currentpage >= getBottomPageNo()) {   
            return getBottomPageNo();   
        }   
        return currentpage + 1;    
    }   
       
    public int getPagecode() {   
        return pagecode;   
    }   
  
    public void setPagecode(int pagecode) {   
        this.pagecode = pagecode;   
    }   
  
    public PageView(int maxresult, int currentpage) {   
        this.maxresult = maxresult;   
        this.currentpage = currentpage;   
    }   
       
    public void setPageData(PageData<T> pageData){   
        setTotalrecord(pageData.getTotalRecords());   
        setRecords(pageData.getResultlist());   
    }   
       
    public long getTotalrecord() {   
        return totalrecord;   
    }   
    public void setTotalrecord(long totalrecord) {   
        this.totalrecord = totalrecord;   
        setTotalpage(this.totalrecord%this.maxresult==0? this.totalrecord/this.maxresult : this.totalrecord/this.maxresult+1);   
    }   
    public List<T> getRecords() {   
        return records;   
    }   
    public void setRecords(List<T> records) {   
        this.records = records;   
    }   
    public PageIndex getPageindex() {   
        return pageindex;   
    }   
    public long getTotalpage() {   
        return totalpage;   
    }   
    public void setTotalpage(long totalpage) {   
        this.totalpage = totalpage == 0 ? 1 : totalpage;   
        this.pageindex = getPageIndex(pagecode, currentpage, totalpage);   
    }   
    public int getMaxresult() {   
        return maxresult;   
    }   
    public int getCurrentpage() {   
        return currentpage;   
    }   
  
  
    public int getLastpage() {   
        return lastpage;   
    }   
       
  public static PageIndex getPageIndex(long viewpagecount, int currentPage, long totalpage){   
        long startpage = currentPage-(viewpagecount%2==0? viewpagecount/2-1 : viewpagecount/2);   
        long endpage = currentPage+viewpagecount/2;   
        if(startpage<1){   
            startpage = 1;   
            if(totalpage>=viewpagecount) endpage = viewpagecount;   
            else endpage = totalpage;   
        }   
        if(endpage>totalpage){   
            endpage = totalpage;   
            if((endpage-viewpagecount)>0) startpage = endpage-viewpagecount+1;   
            else startpage = 1;   
        }   
        return new PageIndex(startpage, endpage);   
  }   
     
    public PageView() {   
            
    }   
       
    public void setCurrentpage(int currentpage) {   
        this.currentpage = currentpage;   
    }   
    public void setMaxresult(int maxresult) {   
        this.maxresult = maxresult;   
    }   
       
  
} 
