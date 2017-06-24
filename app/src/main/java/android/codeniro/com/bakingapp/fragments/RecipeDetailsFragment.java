package android.codeniro.com.bakingapp.fragments;

import android.codeniro.com.bakingapp.R;
import android.codeniro.com.bakingapp.activities.RecipeDetailsActivity;
import android.codeniro.com.bakingapp.adapters.RecipeDetailsAdapter;
import android.codeniro.com.bakingapp.datatypes.Ingredient;
import android.codeniro.com.bakingapp.datatypes.Recipe;
import android.codeniro.com.bakingapp.widgets.UpdateService;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.codeniro.com.bakingapp.activities.RecipeActivity.SELECTED_RECIPES;

/**
 * Created by Dupree on 23/06/2017.
 */

public class RecipeDetailsFragment extends Fragment {

    ArrayList<Recipe> recipe;
    public String recipeName;

    public RecipeDetailsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView;
        TextView textView;

        recipe = new ArrayList<>();


        if(savedInstanceState != null) {
            recipe = savedInstanceState.getParcelableArrayList(SELECTED_RECIPES);

        }
        else {
            recipe =getArguments().getParcelableArrayList(SELECTED_RECIPES);
        }

        List<Ingredient> ingredients = recipe.get(0).getIngredients();
        recipeName=recipe.get(0).getName();

        View rootView = inflater.inflate(R.layout.recipe_detail_fragment_body_part, container, false);
        textView = (TextView)rootView.findViewById(R.id.recipe_detail_text);

        ArrayList<String> recipeIngredientsForWidgets= new ArrayList<>();

        for(Ingredient a: ingredients){
            textView.append("\u2022 "+ a.getIngredient()+"\n");
            textView.append("\t\t\t Quantity: "+a.getQuantity().toString()+"\n");
            textView.append("\t\t\t Measure: "+a.getMeasure()+"\n\n");

            recipeIngredientsForWidgets.add(a.getIngredient()+"\n"+
                    "Quantity: "+a.getQuantity().toString()+"\n"+
                    "Measure: "+a.getMeasure()+"\n");
        }

            Log.d("Pack",ingredients.toString());


        recyclerView=(RecyclerView)rootView.findViewById(R.id.recipe_detail_recycler);
        LinearLayoutManager mLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        RecipeDetailsAdapter mRecipeDetailAdapter =new RecipeDetailsAdapter((RecipeDetailsActivity)getActivity());
        recyclerView.setAdapter(mRecipeDetailAdapter);
        mRecipeDetailAdapter.setMasterRecipeData(recipe,getContext());

        //update widget
        UpdateService.startBakingService(getContext(),recipeIngredientsForWidgets);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelableArrayList(SELECTED_RECIPES, recipe);
        currentState.putString("Title",recipeName);
    }
}
