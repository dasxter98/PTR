package com.example.ptr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;

public class filtroAdapter extends ArrayAdapter<Integer> {
    private LayoutInflater inflater;
    private int[] filtros;

    public filtroAdapter(@NonNull Context context, int[] filtros) {
        super(context, R.layout.item_filtro);
        inflater = LayoutInflater.from(context);
        this.filtros = filtros;
    }

    @Override
    public int getCount() {
        return filtros.length;
    }

    @Override
    public Integer getItem(int position) {
        return filtros[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_filtro, parent, false);
        }
        ImageView imageViewFiltro = view.findViewById(R.id.bt_filtro);
        // Cambiar el tamaño de la imagen ajustando el escalado
        imageViewFiltro.setImageResource(getItem(position));
        imageViewFiltro.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageViewFiltro.setAdjustViewBounds(true);
        imageViewFiltro.setMaxWidth(100); // Establecer el ancho máximo en píxeles
        imageViewFiltro.setMaxHeight(100); // Establecer la altura máxima en píxeles
        return view;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_filtro, parent, false);
        }

        ImageView imageViewFiltro = convertView.findViewById(R.id.iv_filtro);
        // Cambiar el tamaño de la imagen ajustando el escalado
        imageViewFiltro.setImageResource(getItem(position));
        imageViewFiltro.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageViewFiltro.setAdjustViewBounds(true);
        imageViewFiltro.setMaxWidth(100); // Establecer el ancho máximo en píxeles
        imageViewFiltro.setMaxHeight(100); // Establecer la altura máxima en píxeles

        return convertView;
    }
}
