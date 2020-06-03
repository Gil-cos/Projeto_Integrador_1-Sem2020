package com.example.assistentevirtual_idosos;

public enum ModelObject {
    //Definir cores:
    RED(R.string.red, R.layout.view_red),
    BLUE(R.string.blue, R.layout.view_blue),
    GREEN(R.string.green, R.layout.view_green);

    private int mTitleResId;
    private int mLayoutResId;

    //Construtor
    ModelObject(int TitleResId, int LayoutResId){
        mTitleResId = TitleResId;
        mLayoutResId = LayoutResId;
    }

    //Getters

    public int getmTitleResId(){
        return mTitleResId;
    }

    public int getmLayoutResId(){
        return mLayoutResId;
    }
}
