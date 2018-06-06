package com.jennings.jadd.popular_movies_stage2.models;

import java.util.ArrayList;

public class MovieTrailerReviewList {

    public ArrayList<Object> mvReviewObjList;
    public ArrayList<Object> mvTrailerObjList;
    public ArrayList<Object> mvTrailerReviewList;
    public MovieTrailerReviewList(ArrayList<Object> mvRv, ArrayList<Object> mvTr){


    }
    private void combineList(ArrayList<Object> mvRv, ArrayList<Object> mvTr){
        mvTrailerReviewList.addAll(mvRv);
        mvTrailerReviewList.addAll(mvTr);

    }
}
