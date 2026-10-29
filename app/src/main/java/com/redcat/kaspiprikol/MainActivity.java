package com.redcat.kaspiprikol;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainActivity extends Activity {
    private final int RED=Color.rgb(226,38,54), DARK=Color.rgb(42,42,42), PALE=Color.rgb(255,245,246), MUTED=Color.rgb(110,110,110);
    private long balance=10000000L;
    private LinearLayout root, content;
    private final ArrayList<String> history=new ArrayList<>();

    @Override public void onCreate(Bundle b){super.onCreate(b); showHome();}

    private GradientDrawable bg(int color,int radius){
        GradientDrawable d=new GradientDrawable(); d.setColor(color); d.setCornerRadius(radius); return d;
    }
    private TextView txt(String s,int size,int color,boolean bold){
        TextView t=new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(color);
        if(bold)t.setTypeface(Typeface.DEFAULT,Typeface.BOLD); return t;
    }
    private LinearLayout col(){LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);return l;}
    private void pad(View v,int a,int b,int c,int d){v.setPadding(a,b,c,d);}
    private void init(){
        root=col();root.setBackgroundColor(PALE);setContentView(root);
        LinearLayout head=col();head.setBackgroundColor(RED);pad(head,18,14,18,13);root.addView(head);
        head.addView(txt("КрасныйКот",24,Color.WHITE,true));
        head.addView(txt("ПАРОДИЯ • НЕОФИЦИАЛЬНО • НЕ БАНК",10,Color.WHITE,true));
        TextView warning=txt("⚠ ДЕМО · НЕ СВЯЗАНО С KASPI.KZ · ИГРОВЫЕ ДЕНЬГИ",10,RED,true);
        warning.setGravity(Gravity.CENTER);warning.setBackground(bg(Color.rgb(255,224,227),12));pad(warning,8,10,8,10);
        LinearLayout.LayoutParams wp=new LinearLayout.LayoutParams(-1,-2);wp.setMargins(14,12,14,4);root.addView(warning,wp);
        ScrollView scroll=new ScrollView(this);root.addView(scroll,new LinearLayout.LayoutParams(-1,0,1));
        content=col();pad(content,14,8,14,12);scroll.addView(content);
        LinearLayout nav=new LinearLayout(this);nav.setBackgroundColor(Color.WHITE);
        String[] labels={"Главная","Перевод","История"};Runnable[] actions={this::showHome,this::showTransfer,this::showHistory};
        for(int i=0;i<labels.length;i++){final int n=i;Button b=new Button(this);b.setText(labels[i]);b.setTextSize(12);b.setTextColor(RED);b.setAllCaps(false);b.setBackgroundColor(Color.WHITE);b.setOnClickListener(v->actions[n].run());nav.addView(b,new LinearLayout.LayoutParams(0,54,1));}
        root.addView(nav);
    }
    private void heading(String s){TextView t=txt(s,19,DARK,true);pad(t,0,8,0,8);content.addView(t);}
    private LinearLayout card(){
        LinearLayout c=col();c.setBackground(bg(Color.WHITE,18));pad(c,17,16,17,16);
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(0,6,0,10);content.addView(c,p);return c;
    }
    private Button btn(String s,Runnable r){
        Button b=new Button(this);b.setText(s);b.setAllCaps(false);b.setTextSize(14);b.setTextColor(Color.WHITE);b.setBackground(bg(RED,12));b.setPadding(12,8,12,8);b.setOnClickListener(v->r.run());return b;
    }
    private String money(long n){return String.format(Locale.US,"%,d ₸",n).replace(',',' ');}
    private void showHome(){
        init();heading("Главная");
        LinearLayout c=card();c.addView(txt("МОЙ ВЫМЫШЛЕННЫЙ БАЛАНС",11,MUTED,true));
        TextView bal=txt(money(balance),30,DARK,true);pad(bal,0,8,0,4);c.addView(bal);
        c.addView(txt("Игровые цифры. Настоящих денег нет.",12,MUTED,false));
        LinearLayout loan=card();loan.addView(txt("КРЕДИТ МЕЧТЫ 😸",12,MUTED,true));
        TextView amount=txt("10 000 000 ₸",27,RED,true);pad(amount,0,6,0,5);loan.addView(amount);
        loan.addView(txt("Одобрение существует только в этой пародии.",12,DARK,false));
        pad(loan,17,16,17,16);loan.addView(btn("Запустить анимацию одобрения",this::animateApproval));
        LinearLayout quick=card();quick.addView(txt("БЫСТРЫЕ ДЕЙСТВИЯ",12,MUTED,true));quick.addView(btn("Создать шуточный перевод",this::showTransfer));
        TextView note=txt("Никакие переводы не отправляются. Все экраны — демо.",11,RED,true);pad(note,3,8,3,8);content.addView(note);
    }
    private void animateApproval(){
        LinearLayout box=col();pad(box,22,18,22,18);
        TextView label=txt("Кот проверяет заявку… 0%",15,DARK,true);box.addView(label);
        ProgressBar bar=new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);bar.setMax(100);
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,18);p.setMargins(0,18,0,8);box.addView(bar,p);
        box.addView(txt("ШУТКА · НЕ БАНК · ИГРОВЫЕ ДЕНЬГИ",10,RED,true));
        AlertDialog dialog=new AlertDialog.Builder(this).setTitle("Кредит мечты 😸").setView(box).setCancelable(false).create();dialog.show();
        final int[] progress={0};
        Runnable[] tick=new Runnable[1];
        tick[0]=()->{progress[0]+=4;bar.setProgress(progress[0]);label.setText(progress[0]<100?"Считаем воображаемые тенге… "+progress[0]+"%":"Одобрено в воображении! 100%");
            if(progress[0]<100)label.postDelayed(tick[0],70);else label.postDelayed(()->{dialog.dismiss();new AlertDialog.Builder(this).setTitle("🎉 УРА! (ШУТКА)").setMessage("Вымышленный кредит: 10 000 000 ₸\n\nЭто не настоящий кредит и не связано с Kaspi.kz.").setPositiveButton("Понятно",(a,b)->{}).show();},500);};
        label.post(tick[0]);
    }
    private void showTransfer(){
        init();heading("Шуточный перевод");LinearLayout c=card();
        c.addView(txt("Создай вымышленную операцию для розыгрыша.",13,MUTED,false));
        TextView warning=txt("ВСЕ ОПЕРАЦИИ ТОЛЬКО ВНУТРИ ПРИЛОЖЕНИЯ",10,RED,true);pad(warning,0,8,0,8);c.addView(warning);
        EditText name=new EditText(this);name.setSingleLine(true);name.setHint("Имя получателя (например, Друг)");c.addView(name,new LinearLayout.LayoutParams(-1,52));
        EditText amount=new EditText(this);amount.setSingleLine(true);amount.setHint("Сумма в ₸ (например, 5000)");amount.setInputType(InputType.TYPE_CLASS_NUMBER);c.addView(amount,new LinearLayout.LayoutParams(-1,52));
        c.addView(btn("Показать шуточный перевод",()->{
            String who=name.getText().toString().trim(),raw=amount.getText().toString().trim();
            if(who.isEmpty()||raw.isEmpty()){Toast.makeText(this,"Введи имя и сумму для демо",Toast.LENGTH_SHORT).show();return;}
            long value;try{value=Long.parseLong(raw);}catch(Exception e){value=0;}
            if(value<1||value>1000000000L){Toast.makeText(this,"Укажи сумму от 1 до 1 000 000 000 ₸",Toast.LENGTH_SHORT).show();return;}
            String date=new SimpleDateFormat("dd.MM.yyyy HH:mm",Locale.getDefault()).format(new Date());
            history.add(0,"ДЕМО · "+who+" · "+money(value)+" · "+date);receipt(who,value,date);
        }));
    }
    private void receipt(String who,long amount,String date){
        LinearLayout box=col();box.setBackground(bg(Color.WHITE,18));pad(box,22,20,22,20);
        TextView mark=txt("ДЕМО · НЕ БАНК",12,RED,true);mark.setGravity(Gravity.CENTER);box.addView(mark);
        TextView tick=txt("✓",38,RED,true);tick.setGravity(Gravity.CENTER);pad(tick,0,8,0,8);box.addView(tick);
        TextView title=txt("Шуточный перевод создан",19,DARK,true);title.setGravity(Gravity.CENTER);box.addView(title);
        TextView sum=txt("− "+money(amount),27,RED,true);sum.setGravity(Gravity.CENTER);pad(sum,0,14,0,12);box.addView(sum);
        box.addView(txt("Получатель: "+who,14,DARK,false));box.addView(txt("Дата: "+date,12,MUTED,false));
        TextView warning=txt("НЕ ФИНАНСОВАЯ ОПЕРАЦИЯ. Деньги не отправлены. Квитанция создана в приложении-пародии.",11,RED,true);pad(warning,0,16,0,0);box.addView(warning);
        new AlertDialog.Builder(this).setTitle("Квитанция • ДЕМО").setView(box).setPositiveButton("Готово",(d,w)->showHistory()).show();
    }
    private void showHistory(){
        init();heading("История операций");
        if(history.isEmpty()){LinearLayout c=card();c.addView(txt("Пока нет шуточных операций.",14,MUTED,false));c.addView(txt("Создай перевод — он появится здесь.",12,MUTED,false));}
        else for(String item:history){LinearLayout c=card();c.addView(txt("ДЕМО · НЕ БАНК",10,RED,true));pad(c.getChildAt(0),0,0,0,5);c.addView(txt(item,13,DARK,true));}
        content.addView(btn("Очистить историю демо",()->{history.clear();showHistory();}));
        TextView note=txt("История хранится в памяти и исчезнет после закрытия приложения.",11,MUTED,false);pad(note,2,12,2,4);content.addView(note);
    }
}
