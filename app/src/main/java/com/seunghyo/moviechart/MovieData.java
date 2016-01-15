package com.seunghyo.moviechart;

import java.util.ArrayList;

/**
 * Created by SeungHyo on 2016-01-15.
 */
public class MovieData {

    private static MovieData m_instance;

    public static MovieData getInstance() {
        if (m_instance == null) {
            m_instance = new MovieData();
        }
        return m_instance;
    }

    private ArrayList<String> movie_name = new ArrayList<String>();
    private ArrayList<String> open_date = new ArrayList<String>();
    private ArrayList<String> rank = new ArrayList<String>();
    private ArrayList<String> cnt = new ArrayList<String>();

    public ArrayList<String> getCnt() {
        return cnt;
    }

    public void setCnt(ArrayList<String> cnt) {
        this.cnt = cnt;
    }

    public ArrayList<String> getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(ArrayList<String> movie_name) {
        this.movie_name = movie_name;
    }

    public ArrayList<String> getOpen_date() {
        return open_date;
    }

    public void setOpen_date(ArrayList<String> open_date) {
        this.open_date = open_date;
    }

    public ArrayList<String> getRank() {
        return rank;
    }

    public void setRank(ArrayList<String> rank) {
        this.rank = rank;
    }
}
