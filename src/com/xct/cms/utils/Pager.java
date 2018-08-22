package com.xct.cms.utils;
public class Pager {
    private int start = 0;
    private int end = 0;
    private int totalCount = 0;
    private int currentPage = 0;
    private int totalPage = 0;
    private int pageSize = 20;
    
	
	public Pager(int total,int page,int size) {
        totalCount = total;
        currentPage = page;
        pageSize = size;
        totalPage = (totalCount - 1)/ pageSize + 1;
        if (page < 1) page = 1;
		if (page > totalPage) page = totalPage;
		currentPage=page;
        start = (page-1) * pageSize + 1;
        if(total%size==0) end = total/size;
        else  end = total/size+1;
    }
		
    public void setPageSize(int pageSize) { 
	    this.pageSize = pageSize; 
	}
	
    public int getPageSize() {
		return pageSize;
	}

	public void setTotalCount(int totalCount) { 
	    this.totalCount = totalCount; 
	}
  
    public void setCurrentPage(int currentPage) { 
	    this.currentPage = currentPage; 
	}
    
	public int getTotalPage(){
	    return totalPage;
	}
	
	public int getCurrentPage(){
	    return currentPage;
	}
	
	public boolean isFirstPage() { 
	    return (currentPage == 1); 
	}
	
    public boolean isLastPage() { 
	    return (currentPage == totalPage); 
	}
	
    public int getStart() {
	    return start; 
	}
  
    public int getEnd() { 
	    return end; 
	}
}