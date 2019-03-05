package com.example.joousope.bakdinle;

import android.net.Uri;
import android.support.annotation.Keep;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;
@Keep
public class Question {
    public String Name;
    public String Image;

    public Question()
    {
    }

    public Question(String Name, String Image) {
        this.Name = Name;
        this.Image = Image;
    }
    @Exclude
    public String getName() {
        return Name;
    }
    @Exclude
    public void setName(String Name) {
        this.Name = Name;
    }
    @Exclude
    public String getImage() {
        return Image;
    }
    @Exclude
    public void setImage(String Image) {
        this.Image = Image;
    }
    @Exclude
    public Map<String,Object> toMap(){
        HashMap<String,Object> result=new HashMap<>();
        result.put("Name",Name);
        result.put("Image",Image);
        return result;
    }
}
