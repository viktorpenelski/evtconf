//package byfo.evtconf.spreadsheet.tournaments
//
//import android.util.Log
//import byfo.evtconf.spreadsheet.SpreadsheetEntry
//import org.json.JSONException
//import org.json.JSONObject
//
///**
// * Created by victo on 3/16/2018.
// */
///**
// * This class represents a single entry fetched from the MainStage sheet of the spreadsheet.
// */
//data class TournamentSpreadsheetEntry(val title: String, val picture: String, val redirectUrl: String) : SpreadsheetEntry {
//
//    override fun isEmpty(): Boolean {
//        return title.isEmpty() || picture.isEmpty() || redirectUrl.isEmpty()
//    }
//
//    companion object {
//
//        private const val TITLE = "gsx\$title"
//        private const val PICTURE = "gsx\$picture"
//        private const val LINK = "gsx\$url"
//
//        private const val VALUE = "\$t"
//
//        override fun fromJSONObject(obj: JSONObject): TournamentSpreadsheetEntry {
//            return try {
//                TournamentSpreadsheetEntry(
//                        obj.getJSONObject(TITLE).getString(VALUE),
//                        obj.getJSONObject(PICTURE).getString(VALUE),
//                        obj.getJSONObject(LINK).getString(VALUE))
//            } catch (e: JSONException) {
//                Log.e("deserialization", "Something went wrong during deserialization of remote json - ${e.stackTrace}")
//                TournamentSpreadsheetEntry("", "", "")
//            }
//        }
//    }
//}