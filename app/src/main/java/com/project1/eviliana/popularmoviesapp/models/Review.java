package com.project1.eviliana.popularmoviesapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This is a POJO class for review objects
 * For parcel i used the Android Parcelable code generator plugin
 */
public class Review implements Parcelable {

        private String id;
        private String reviewAuthor;
        private String reviewContent;

        public Review (String id,String reviewAuthor,String reviewContent){
            this.id = id;
            this.reviewAuthor = reviewAuthor;
            this.reviewContent = reviewContent;
        }
        public String getId() {
            return id;
        }

        public String getReviewAuthor() {
            return reviewAuthor;
        }

        public String getReviewContent() {
            return reviewContent;
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.reviewAuthor);
        dest.writeString(this.reviewContent);
    }

    protected Review(Parcel in) {
        this.id = in.readString();
        this.reviewAuthor = in.readString();
        this.reviewContent = in.readString();
    }

    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel source) {
            return new Review(source);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}
