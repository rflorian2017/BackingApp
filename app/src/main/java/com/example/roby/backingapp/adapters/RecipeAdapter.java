package com.example.roby.backingapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roby.backingapp.R;
import com.example.roby.backingapp.model.Recipe;
import com.example.roby.backingapp.ui.RecipeViewHolder;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Store a member variable for the Recipes
    private List<Recipe> items;

    // Store the context for easy access
    private Context mContext;

    //Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    // Click handler to allow transition to a detail activity
    final private RecipeAdapterOnClickHandler mClickHandler;

    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe param);

    }

    // Pass in the click handler to the constructor
    public RecipeAdapter(RecipeAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        RecyclerView.ViewHolder viewHolder = null;

        // Inflate the custom layout
        View recipeView = inflater.inflate(R.layout.recipe_card, viewGroup, false);
        // Return a new holder instance
        viewHolder = new RecipeViewHolder(recipeView, mClickHandler);
        ((RecipeViewHolder) viewHolder).setRecipes(items);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        RecipeViewHolder recipeViewModel = (RecipeViewHolder) viewHolder;
        recipeViewModel.getmRecipeName().setText(items.get(position).getRecipeName());
    }

    @Override
    public int getItemCount() {
        if (null == items) return 0;
        //assume here that only one cursor is present. For now, as the movie app contains only one cursor, it is ok.
        /* TODO: In the future, try here to make it generic */
        if ((items.size() == 1) && (items.get(0) instanceof Cursor)) {
            return ((Cursor) items.get(0)).getCount();
        }
        return items.size();
    }

    public void setRecipeData(List<Recipe> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
