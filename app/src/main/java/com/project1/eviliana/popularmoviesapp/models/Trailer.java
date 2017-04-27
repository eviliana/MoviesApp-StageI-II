package com.project1.eviliana.popularmoviesapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This is a POJO class for review objects
 * For parcel i used the Android Parcelable code generator plugin
 */
public class Trailer implements Parcelable {

        private String id;
        private String videoName;
        private String videoPath;

        public Trailer(String id, String videoName, String videoPath){
            this.id = id;
            this.videoName = videoName;
            this.videoPath = videoPath;
        }
        public String getId() {
            return id;
        }

        public String getVideoName() {
            return videoName;
        }

        public String getVideoPath() {
            return videoPath;
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.videoName);
        dest.writeString(this.videoPath);
    }

    protected Trailer(Parcel in) {
        this.id = in.readString();
        this.videoName = in.readString();
        this.videoPath = in.readString();
    }

    public static final Parcelable.Creator<Trailer> CREATOR = new Parcelable.Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel source) {
            return new Trailer(source);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };
}
