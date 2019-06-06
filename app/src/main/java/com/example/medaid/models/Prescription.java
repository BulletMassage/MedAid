package com.example.medaid.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


@Entity(tableName = "prescriptions")
public class Prescription implements Parcelable, Cloneable {

    /*--------------------Variables--------------------*/

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "quantity")
    private int quantity;

    @ColumnInfo (name = "schedule")
    private List<WeeklySchedule> schedule = new ArrayList<>();


    /*--------------------Constructors--------------------*/

    public Prescription(String title, String description, int quantity, List<WeeklySchedule> schedule) {
        this.title = title;
        this.description = description;
        this.quantity = quantity;
        this.schedule = schedule;
    }

    // Copy Constructor
    @Ignore
    public Prescription(Prescription prescription) {
        this.title = prescription.getTitle();
        this.description = prescription.getDescription();
        this.quantity = prescription.getQuantity();

        List<WeeklySchedule> weeklySchedule = new ArrayList<>();
        for (WeeklySchedule instance : prescription.getSchedule()) {
            weeklySchedule.add(new WeeklySchedule(instance));
        }
        this.schedule = weeklySchedule;
    }

    @Ignore
    public Prescription(){quantity = -1;}


    /*----------------Getter/Setter/Helper----------------*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<WeeklySchedule> getSchedule() {
        return schedule;
    }

    public void addWeeklySchedule(WeeklySchedule weeklySchedule) {
        schedule.add(weeklySchedule);
    }

    public void deleteWeeklySchedule (WeeklySchedule weeklySchedule) {
        schedule.remove(weeklySchedule);
    }

    public boolean timeExists (String time) {
        for (WeeklySchedule instance : schedule) {
            if (instance.getTime().matches(time)) {
                return true;
            }
        }
        return false;
    }


    /*--------------------Parcelable--------------------*/
    protected Prescription(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.quantity = in.readInt();
        this.schedule = in.createTypedArrayList(WeeklySchedule.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeInt(this.quantity);
        dest.writeTypedList(this.schedule);
    }

    public static final Creator<Prescription> CREATOR = new Creator<Prescription>() {
        @Override
        public Prescription createFromParcel(Parcel source) {
            return new Prescription(source);
        }

        @Override
        public Prescription[] newArray(int size) {
            return new Prescription[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
