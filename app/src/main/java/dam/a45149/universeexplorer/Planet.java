package dam.a45149.universeexplorer;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.text.Editable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Planet {
    private int index ; // planet index in resource file
    private String name ; // name
    private double mass ; // mass
    private double gravity ; // gravity
    private int numColonies = 1; // number of colonies
    private int numMilitaryBases = 0; // number of military bases
    private boolean forceField = false ; // forceField activated : true or false
    private int imgSmallResourceValues ; // small image resource ID of the planet


    public Planet(int index, String name, double mass, double gravity, int imgSmallResourceValues) {
        this.index = index;
        this.name = name;
        this.mass = mass;
        this.gravity = gravity;
        this.imgSmallResourceValues = imgSmallResourceValues;
    }
    @SuppressLint("ResourceType")
    public static Planet getPlanetFromResource(int index, Resources res){
        TypedArray arrayPlanets = res.obtainTypedArray(R.array.planets);
        int idPlanet = arrayPlanets.getResourceId(index, -1);
        TypedArray planet = res.obtainTypedArray(idPlanet);
        String planetName = planet.getString(0);
        float planetMass = planet.getFloat(1, 0f);
        float planetGravity = planet.getFloat(2, 0f);
        int imgsmallid = planet.getResourceId(3, 0);
        int bigImg = planet.getResourceId(4, 0);

        return new Planet(index, planetName, planetMass, planetGravity, imgsmallid );
    }
    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    public int getNumColonies() {
        return numColonies;
    }

    public int getNumMilitaryBases() {
        return numMilitaryBases;
    }

    public boolean isForceField() {
        return forceField;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setNumColonies(int numColonies) {
        this.numColonies = numColonies;
    }

    public void setNumMilitaryBases(int numMilitaryBases) {
        this.numMilitaryBases = numMilitaryBases;
    }

    public void setForceField(boolean forceField) {
        this.forceField = forceField;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public double getMass() {
        return mass;
    }

    public double getGravity() {
        return gravity;
    }

    public int getImgSmallResourceValues() {
        return imgSmallResourceValues;
    }


}
