package com.ronem.carwash.view.editprofile;

/**
 * Created by ram on 8/5/17.
 */

public class EditProfilePresenterImpl implements EditProfilePresenter {
    private EditProfileView profileView;

    @Override
    public void addView(EditProfileView v) {
        this.profileView = v;
    }

    @Override
    public void editProfile() {

    }
}
