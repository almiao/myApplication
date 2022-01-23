package com.example.myapplication.bean;


import java.util.List;


public class PageVO<T> {

    private  long counts; //总个数
    private  long pagesize; //每页个数
    private  long pages; //总页数
    private  long page; //当前页
    private List<T> items; //数据记录

}
