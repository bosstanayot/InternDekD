package dekd.intern.bosstanayot.interndekd;

import android.arch.lifecycle.ViewModel;

import org.json.JSONArray;

public class JsonViewModel extends ViewModel {
    private JSONArray jsonArray = new JSONArray();

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }
}
