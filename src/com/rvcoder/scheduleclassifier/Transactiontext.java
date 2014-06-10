package com.rvcoder.scheduleclassifier;

import java.io.Serializable;

/**
 * Simple container object for contact data
 *
 * Created by mgod on 9/12/13.
 * @author mgod
 */
public class Transactiontext implements Serializable{
    private String Tags;

    public Transactiontext(String n) {
        Tags = n;
    }

    public String getName() { return Tags; }

    @Override
    public String toString() { return Tags; }
}
