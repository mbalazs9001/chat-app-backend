package com.mng.hermes.dto;

import com.mng.hermes.model.User;

public class UserDTO {

    private int id;
    private String nickName;
    private String introduction;
    private String givenName;
    private String familyName;
    private String pictureUrl;

    public UserDTO(User user) {
        this.id = user.getId();
        this.nickName = user.getNickName();
        this.introduction = user.getIntroduction();
        this.givenName = user.getGivenName();
        this.familyName = user.getFamilyName();
        this.pictureUrl = user.getPicture();
    }

    public int getId() {
        return id;
    }

    public String getNickName() {
        return nickName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }
}
