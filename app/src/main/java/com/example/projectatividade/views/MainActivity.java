/*
 *@author:<Wallace Moura Machado de Oliveira;1110482413004>
 */

package com.example.projectatividade.views;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.projectatividade.R;

 public class MainActivity extends AppCompatActivity {
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
         setSupportActionBar(findViewById(R.id.toolbar));
     }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.menu_main, menu);
         return true;
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
         if(item.getItemId() == R.id.menu_cadastrar_professor) {
             transaction.replace(R.id.container_main, new CadastrarProfessorFragment());
             transaction.commit();
             return true;
         }
         else if(item.getItemId() == R.id.menu_cadastrar_aluno) {
             transaction.replace(R.id.container_main, new CadastrarAlunoFragment());
             transaction.commit();
             return true;
         }
          else if(item.getItemId() == R.id.menu_login) {
                 transaction.replace(R.id.container_main, new LoginFragment());
                 transaction.commit();
                 return true;
         }
         return super.onOptionsItemSelected(item);
     }
 }