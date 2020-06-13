package com.example.assistentevirtual_idosos;

public enum ModelObject {
    //Definir cores:
    WIFI(R.string.wifi, R.layout.wifi),
    FOTO(R.string.foto, R.layout.foto),
    BLUETOOTH(R.string.bluetooth, R.layout.bluetooth);

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
