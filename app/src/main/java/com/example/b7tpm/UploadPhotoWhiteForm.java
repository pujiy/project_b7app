package com.example.b7tpm;

public class UploadPhotoWhiteForm {
    private String mNamePhoto;
    private String mImageUrl;

    public UploadPhotoWhiteForm() {
        //empty constructor needed
    }

    public UploadPhotoWhiteForm(String namePhoto, String imaageUrl) {
        if(namePhoto.trim().equals("")) {
            namePhoto = "No Name";
        }

        mNamePhoto = namePhoto;
        mImageUrl = imaageUrl;
    }

    public String getNamePhoto() {
        return mNamePhoto;
    }

    public void setNamePhoto(String namePhoto) {
        mNamePhoto = namePhoto;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String ImageUrl) {
        mImageUrl = ImageUrl;
    }
}
