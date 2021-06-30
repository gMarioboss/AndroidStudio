package com.example.notes.Data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.notes.Utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataSourceFirebaseImpl implements DataSource {
    private static final String TAG = "[FirebaseImpl]";

    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    private CollectionReference collection = store.collection(Constants.NOTES_COLLECTION);

    private List<DataNote> noteList = new ArrayList<>();

    @Override
    public DataSource init(DataSourceResponse dataSourceResponse) {
        collection.orderBy(Constants.DATE, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            noteList = new ArrayList<>();
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Map<String, Object> doc = document.getData();
                                String id = document.getId();
                                DataNote dataNote = DataNoteMapping.toDataNote(id, doc);
                                noteList.add(dataNote);
                            }
                            dataSourceResponse.initialized(DataSourceFirebaseImpl.this);
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.d(TAG, "get failed with ", e);
                    }
                });
        return this;
    }

    @Override
    public DataNote getDataNote(int position) {
        return noteList.get(position);
    }

    @Override
    public int getSize() {
        if (noteList == null){
            return 0;
        }
        return noteList.size();
    }

    @Override
    public void deleteCardData(int position) {
        collection.document(noteList.get(position).getId()).delete();
        noteList.remove(position);
    }

    @Override
    public void updateCardData(int position, DataNote dataNote) {
        collection.document(dataNote.getId()).set(DataNoteMapping.toDocument(dataNote));
    }

    @Override
    public void addCardData(DataNote dataNote) {
        collection.add(DataNoteMapping.toDocument(dataNote))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>(){
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        dataNote.setId(documentReference.getId());
                    }
                });
    }
}
