package org.twspring.capstone1.Model;

import org.hibernate.validator.constraints.Range;

public class Review {
    private int id;
    private int userId;
    private int productId;
    private boolean wasProductGood; //yes, no
    private boolean wasShippingGood; //yes, no
    @Range(min = 0, max = 5)
    private int score;
    public String comment;
}
