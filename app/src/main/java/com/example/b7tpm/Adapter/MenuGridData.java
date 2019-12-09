package com.example.b7tpm.Adapter;

import com.example.b7tpm.Model.MenuGrid;
import com.example.b7tpm.R;

import java.util.ArrayList;

public class MenuGridData {

    private static String[] menuNames = {
            "Buat Form Baru",
            "Administrasi Form",
            "Status Form",
            "Barcode Scanner",
            "Menu RCA",
            "Informasi TPM"
    };

    private static int[] menuImages = {
            R.drawable.ic_new_form,
            R.drawable.ic_administrasi,
            R.drawable.ic_form_status,
            R.drawable.ic_barcode,
            R.drawable.ic_rca,
            R.drawable.ic_info
    };

    public static ArrayList<MenuGrid> getListData() {
        ArrayList<MenuGrid> list = new ArrayList<>();

        for (int position = 0; position < menuNames.length; position++) {
            MenuGrid menuGrid = new MenuGrid();
            menuGrid.setName(menuNames[position]);
            menuGrid.setPhoto(menuImages[position]);
            list.add(menuGrid);
        }
        return list;
    }
}