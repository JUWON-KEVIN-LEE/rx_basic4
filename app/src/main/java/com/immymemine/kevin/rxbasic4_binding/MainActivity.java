package com.immymemine.kevin.rxbasic4_binding;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.Random;

import io.reactivex.Observable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable<TextViewTextChangeEvent> id_emitter = RxTextView.textChangeEvents(findViewById(R.id.editText_email));
        // InitialValueObservable<TextViewTextChangeEvent> ivo_email = RxTextView.textChangeEvents(findViewById(R.id.editText_email));

        Observable<TextViewTextChangeEvent> pw_emitter  = RxTextView.textChangeEvents(findViewById(R.id.editText_pw));
        // InitialValueObservable<TextViewTextChangeEvent> ivo_pw  = RxTextView.textChangeEvents(findViewById(R.id.editText_pw));

        Observable.combineLatest(
                id_emitter, pw_emitter, ( id, pw ) -> id.text().length() >= 5 && pw.text().length() >= 8
        ).subscribe(
            flag -> findViewById(R.id.button2).setEnabled(true)
        );

        RxTextView.textChangeEvents(findViewById(R.id.editText))
                .subscribe( ch -> Log.i("RxBinding", "word : " + ch.text()));

        RxView.clicks(findViewById(R.id.button))
                // return ( button type ) object
                .map( button -> "random value : " + new Random().nextInt())
                .subscribe( text -> {
                    ((EditText)findViewById(R.id.editText)).setText(text);
                });
    }
}
