package android.codeniro.com.bakingapp.utils;

import android.codeniro.com.bakingapp.datatypes.Recipe;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Tope on 23/06/2017.
 */

public interface Recipies {
    @GET("baking.json")
    Call<ArrayList<Recipe>> getRecipe();
}
