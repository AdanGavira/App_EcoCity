package com.example.app_ecocity;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FirestoreHelper {
    private FirebaseFirestore db;
    private CollectionReference incidenciasRef;

    public FirestoreHelper() {
        db = FirebaseFirestore.getInstance();
        incidenciasRef = db.collection("incidencias");
    }

    public void insertarIncidencia(Incidencia incidencia) {
        Log.d("FIRESTORE_TEST", "Dentro de insertarIncidencia");
        incidenciasRef.add(incidencia).addOnSuccessListener(doc ->
                        Log.d("FIRESTORE_TEST", "Guardado OK: " + doc.getId())
                )
                .addOnFailureListener(e ->
                        Log.e("FIRESTORE_TEST", "Error guardando", e)
                );
    }

    public void obtenerIncidenciasUsuario(String userId,
                                          OnSuccessListener<List<Incidencia>> listener) {

        incidenciasRef
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(query -> {

                    List<Incidencia> lista = new ArrayList<>();
                    for (DocumentSnapshot doc : query) {
                        Incidencia i = doc.toObject(Incidencia.class);
                        if (i != null) {
                            i.setId(doc.getId());
                            lista.add(i);
                        }
                    }
                    listener.onSuccess(lista);
                });
    }

    public void obtenerIncidenciaPorId(String id, OnSuccessListener<Incidencia> listener) {
        incidenciasRef.document(id).get().addOnSuccessListener(doc -> {
            Incidencia i = doc.toObject(Incidencia.class);
            if (i != null) i.setId(doc.getId());
            listener.onSuccess(i);
        });
    }

    public void actualizarIncidencia(Incidencia incidencia) {
        incidenciasRef.document(incidencia.getId()).set(incidencia);
    }


    public void borrarIncidencia(String id) {
        incidenciasRef.document(id).delete();
    }

}
