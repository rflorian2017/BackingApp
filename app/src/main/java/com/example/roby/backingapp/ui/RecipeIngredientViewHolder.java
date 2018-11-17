package com.example.roby.backingapp.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.example.roby.backingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeIngredientViewHolder extends ViewHolder{
    @BindView(R.id.ingredients_list_tv)
    TextView recipeIngredientsTv;

    public RecipeIngredientViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public TextView getRecipeIngredientsTv() {
        return recipeIngredientsTv;
    }
}
