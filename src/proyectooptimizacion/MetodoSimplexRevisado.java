
package proyectooptimizacion;

import java.util.ArrayList;

public class MetodoSimplexRevisado {
    
    public static ArrayList<String> salidaFinal = new ArrayList<String>();
    
    public void metodo(int n, int m,int fo,double c[], double a[][],int r[]){
        salidaFinal.clear();
        String prints = "";
        int fotemp = fo,condicion = 0,i,j,bandera,acotada;
        //************METODO SIMPLEX REVISADO****************
    //*****Caso todas las restricciones menor igual******

    bandera=0;
    for(i=0;i<m;i++)
        if(r[i]!=1)
            
            bandera++;

    if(bandera==0){
       
        double cb[] = new double[n+m];
        double mat[][] = new double[m][n+m];
        double valb[] =  new double[m];
        double valbtemp[] =  new double[m];
        double b[][] = new double[m][m];
        double binv[][]=new double[m][m];
        int xb[] = new int[m];
        int xnb[] = new int[n];
        double z;
        double cbxbinv[] =  new double[m];
        double cbxbinxaj[] =new double[n];
        double zj_cj[] =  new double[n];
        double varEtemp = 0,varStemp = 0;
        int k = 0,varE = 0,varS = 0,temp = 0;
        double yai[] = new double[m];
        double E[][] = new double[m][m];
        double binvtemp[][] = new double[m][m];
        double tempe;
        double e[] = new double[m];
        int posvars = 0;
        double acotemp= 0;

        //*****Matriz de coef de la funcion objetivo*****
        for(j=0;j<m+n;j++){
            if(j<n)
                cb[j]=c[j];
            else
                cb[j]=0;
        }

        //*****Cracion de la matriz agregando las var de holgura****
        for(j=0;j<m;j++)
           for(i=0;i<n;i++)
                mat[j][i]=a[j][i];
        for(j=0;j<m;j++)
           for(i=n;i<n+m;i++)
                if(j==i-n)
                    mat[j][i]=1;
                else
                    mat[j][i]=0;

        //****Matrices que señalan las var basicas y no basicas*****
        for(j=0;j<m;j++){
            xb[j]=j+n+1;
        }
        for(j=0;j<n;j++){
            xnb[j]=j+1;
        }

        //*********Matriz b y b inversa de las var basicas**********
        for(j=0;j<m;j++)
           for(i=n;i<n+m;i++)
                b[j][i-n]=mat[j][i];
            //*****La primer matriz b inversa es la misma******
        for(j=0;j<m;j++)
           for(i=n;i<n+m;i++)
                binv[j][i-n]=mat[j][i];

        prints += "*******Matriz con los valores XBi********\n";
        for(j=0;j<m;j++){
            valb[j]=a[j][n];
            prints += String.format("%.2f",valb[j]) + "\n";
        }

        prints += "******Impresion de la matriz******\n";
        for(j=0;j<m;j++){
           for(i=0;i<n+m;i++){
                prints += String.format("%.2f ",mat[j][i]);
           }
           prints += "\n";
        }//*********

        //************Empiezan las iteraciones**************
        //**************************************************
        condicion=0;
        do{
            acotada=0;
            prints += "\n************Iteraccion"+ (condicion+1) +"***************\n\n" ;

            //*******Impresion var basicas y no basicas******
            prints += "Variables basicas:\n";
            for(j=0;j<m;j++)
                prints+= "\tX"+ xb[j]+ "\n";
            prints+= "Variables no basicas:\n";
            for(j=0;j<n;j++)
                prints += "\tX" + xnb[j] +"\n";

            //*******Impresion de las matrices b y b inv*****
            prints+= "Matriz B:\n";
            for(j=0;j<m;j++){
                prints += "\t";
                for(i=0;i<m;i++)
                    prints += String.format("%.2f ",b[j][i]);
                prints += "\n";
            }
            prints += "Matriz B inversa:\n";
            for(j=0;j<m;j++){
                prints += "\t";
                for(i=0;i<m;i++)
                    prints += String.format("%.2f ",binv[j][i]);
                prints+= "\n";
            }

            //*****Impresion de Z*******
            for(i=0;i<m;i++)
                    valbtemp[i]=0;
            for(j=0;j<m;j++){
                for(i=0;i<m;i++){
                    valbtemp[j]+=binv[j][i]*a[i][n];
                }
            }//****Calculo de la nueva xb****
            z=0;
            prints += "\n";
            for(i=0;i<m;i++)
                valb[i]=valbtemp[i];
            //**Calculo de z**
            for(i=0;i<m;i++){
                z+=cb[xb[i]-1]*valb[i];
            }
            prints += "\nEl valor de Z en la iteraccion " + String.valueOf(condicion) + "es" +  String.format("%.2f",z) ;

            //**********Variable de entrada*************
            for(j=0;j<m;j++)
                cbxbinv[j]=0;
            for(j=0;j<n;j++)
                cbxbinxaj[j]=0;

            for(j=0;j<m;j++)
                for(i=0;i<m;i++)
                    cbxbinv[j]+=cb[xb[i]-1]*binv[i][j];
            for(j=0;j<n;j++)
                for(i=0;i<m;i++)
                    cbxbinxaj[j]+=cbxbinv[i]*mat[i][xnb[j]-1];
            for(j=0;j<n;j++)
                zj_cj[j]=cbxbinxaj[j]-cb[xnb[j]-1];
        prints += "\n\nmatriz Zj-Cj: \n";
        for(j=0;j<n;j++)
        prints += String.format("%.2f ",zj_cj[j]);

            //********Condicion para seleccionar var de entrada********
            if(fo==1){//********Maximizar se busca el menor negativo**********
                varEtemp=zj_cj[0];
                for(i=0;i<n;i++){
                    if(zj_cj[i]<varEtemp)
                        varEtemp=zj_cj[i];
                }
                if(varEtemp<0){
                    for(i=0;i<n;i++){
                        if(varEtemp==zj_cj[i]){
                            varE=i;
                            condicion++;
                            break;
                        }
                    }
                }
                else{
                    condicion=0;
                }
            }
            else{//*******Minimizar se busca el mayor positivo***********
                varEtemp=zj_cj[0];
                for(i=0;i<n;i++){
                    if(zj_cj[i]>varEtemp)
                        varEtemp=zj_cj[i];
                }
                if(varEtemp>0){
                    for(i=0;i<n;i++){
                        if(varEtemp==zj_cj[i]){
                            varE=i;
                            condicion++;
                            break;
                        }
                    }
                }
                else{
                    condicion=0;
                }
            }

            //***********Condicion para seleccionar variable de salida************
            varStemp=-1;
            if(condicion!=0){
                for(j=0;j<m;j++){
                    yai[j]=0;
                }
                for(j=0;j<m;j++){
                    for(i=0;i<m;i++){
                        yai[j]+=binv[j][i]*mat[i][xnb[varE]-1];
                    }
                }
                for(j=0;j<m;j++)
                    e[j]=yai[j];
                for(i=0;i<m;i++){
                    if(yai[i]>0.01){
                        varStemp=((valb[i])/(yai[i]));
                        yai[i]=varStemp;
                    }
                    else{
                        yai[i]=-1;
                    }
                }
                for(i=0;i<m;i++){
                    if(yai[i]>0.01){
                        acotemp=yai[i];
                        break;
                    }
                    else
                        acotemp=-1;
                }
                if(varStemp==-1)//********Si no hay var de salida mayor a cero, entonces la sol no esta acotada*********
                {
                    acotada=1;
                    break;
                }
                for(i=0;i<m;i++){
                    if(yai[i]<varStemp&&yai[i]!=-1)
                        varStemp=yai[i];
                }
                if(varStemp>0){
                    for(i=0;i<m;i++){
                        if(varStemp==yai[i]){
                            varS=i;
                            break;
                        }
                    }
                }

                //***********Actualizar Xb y Xnb*****************
                temp=xb[varS];
                xb[varS]=xnb[varE];
                xnb[varE]=temp;

                //****************Calcular las nuevas matrices b y b inversa********************
                for(i=0;i<m;i++){
                    for(j=0;j<m;j++){
                        b[j][i]=mat[j][xb[i]-1];
                    }
                }
                //*****b inversa*******
                tempe=e[varS];
                for(j=0;j<m;j++){
                    if(varS==j)
                        e[j]=1/tempe;
                    else
                        e[j]=-e[j]/tempe;
                }
                for(j=0;j<m;j++){
                    for(i=0;i<m;i++){
                        if(j==i)
                            E[j][i]=1;
                        else
                            E[j][i]=0;
                    }
                }
                for(j=0;j<m;j++){
                    for(i=0;i<m;i++){
                        if(varS==i)
                            E[j][i]=e[j];
                    }
                }
            prints += "\n\nimpresion de la matriz E\n";
            for(j=0;j<m;j++){
                for(i=0;i<m;i++){
                    prints += String.format("%.2f ",E[j][i]);
                }
                prints += "\n";
            }
                for(j=0;j<m;j++){
                    for(i=0;i<m;i++){
                        binvtemp[j][i]=binv[j][i];
                        binv[j][i]=0;
                    }
                }
                for(j=0;j<m;j++){
                    for(k=0;k<m;k++){
                        for(i=0;i<m;i++){
                            binv[j][k]+=E[j][i]*binvtemp[i][k];
                        }
                    }
                }
                for(j=0;j<m;j++)
                    yai[j]=0;
            }
            
            salidaFinal.add(prints);   
            prints = "";
        }while(condicion!=0);
        if(acotada==1){
            prints += "\n\n*********La solucion no esta acotada*********\n\n";
            
            salidaFinal.add(prints);
            prints = "";
        }
        else{
            prints +="\n\n*******Se llego a la solucion optima con**********\n\n";
            for(i=0;i<m;i++){
                prints += "X"+xb[i] +" = " + String.format("%.2f",valb[i]) + "\n";
            }
            for(i=0;i<n;i++){
                prints += "X" + xnb[i] + " = 0\n";
            }
            prints +=  String.format("\tCon Z = %.2f",z);
           
            salidaFinal.add(prints);
            prints = "";
        }
    }else{
        prints += "\n\n**********************************\n************FASE 1****************\n**********************************\n\n";
        int nvara=0;//numero de variables artificiales
        int nvare=0;//numero de variables de exceso
        int nvarai=0;//numero de variables artificiales de igualdades

        //*******Numero de var artificiales y de exceso********
        for(i=0;i<m;i++){
            if(r[i]==3){//mayor igual
                nvara++;
                nvare++;
            }
            if(r[i]==2){//igual
                nvara++;
                nvarai++;
            }
        }
        fo=2;
        double cw[] = new double[n+m+nvara-nvarai];
        double cb[] = new double[n+m-nvarai];
        double mat[][] =  new double [m][n+m+nvara-nvarai];
        double valb[] = new double[m];
        double valbtemp[] = new double[m];
        double b[][] = new double[m][m];
        double binv[][] = new double[m][m];
        Variable xb[] = new Variable[m];
        for(int k = 0;k<m;k++){
            xb[k] = new Variable();
        }
        Variable xnb[] = new Variable[n+nvare];
        for(int k = 0;k<n+nvare;k++){
            xnb[k] = new Variable();
        }
        
        double z,w;
        double wj_cj[] = new double[n+nvara-nvarai];

        double cbxbinv[] = new double[m],cbxbinxaj[] = new double[n+nvara-nvarai],zj_cj[] = new double[n];
        double varEtemp,varStemp;
        int k,varE = 0,varS = 0,temp,tempa;
        double yai[] = new double[m];
        double E[][] = new double[m][m],e[] = new double[m],binvtemp[][] = new double[m][m],tempe;
        int posvars;
        double acotemp;
        int terminar;

        //****Matrices que señalan las var basicas y no basicas*****
        for(i=n;i<n+nvare;i++){
            xnb[i].posision=0;
        }
        bandera=0;
        for(j=0;j<m;j++){
            if(r[j]==1){
                xb[j].posision=j+n;
                xb[j].artificial=0;bandera++;
            }
            if(r[j]==2){
                xb[j].posision=j+n;
                xb[j].artificial=1;bandera++;
                
            }
            if(r[j]==3){
                xb[j].posision=j+n+m-bandera;
                xb[j].artificial=1;
                
                i=0;
                for(i=n;i<n+nvare;i++){
                    if(xnb[i].posision==0){
                        xnb[i].posision=xb[j].posision-m+bandera;
                        xnb[i].artificial=0;
                        
                        break;

                    }
                }

            }
        }
        for(j=0;j<n;j++){
            xnb[j].posision=j+1;
            xnb[j].artificial=0;
        }

        //*****Matriz de coef de la funcion objetivo*****
        for(j=0;j<m+n-nvarai;j++){
            if(j<n)
                cb[j]=c[j];
            else
                cb[j]=0;
        }

        //*****Matriz de coef de la funcion objetivo artificial*****
        for(j=0;j<m+n+nvara-nvarai;j++){
            cw[j]=0;
        }
        for(j=0;j<m;j++){
            if(xb[j].artificial==1){
                cw[xb[j].posision-1]=1;
            }
        }
        prints += "\n\nMIN W =";
        for(j=0;j<m+n+nvara-nvarai;j++){
            prints += String.format("%.2f ",cw[j]);
        }

        //*****Creacion de la matriz agregando las var de holgura, exceso y var artificiales****
        for(j=0;j<m;j++)
            for(i=0;i<n;i++)
                mat[j][i]=a[j][i];
        for(j=0;j<m;j++){
            for(i=n;i<n+m+nvara-nvarai;i++){
                mat[j][i]=0;
            }
        }
        for(j=0;j<m;j++){
            for(i=n;i<n+m+nvara-nvarai;i++){
                if(r[j]==1){
                    if(j==i-n){
                        mat[j][i]=1;
                    }else
                        mat[j][i]=0;
                }
                if(r[j]==3){//mayor igual
                    if(j==i-n)
                        mat[j][i]=-1;
                    else
                        mat[j][xb[j].posision-1]=1;
                }
                if(r[j]==2){//igual
                    if(j==i-n)
                        mat[j][i]=1;
                    else
                        mat[j][i]=0;
                }
            }
        }

        //impresion matriz completa
        prints+= "\n\nMatriz con variables de holgura, exceso y artificiales\n\n";
        for(j=0;j<m;j++){
            prints += "\t";
            for(i=0;i<n+m+nvara-nvarai;i++){
                prints+= String.format("%.2f ",mat[j][i]);
            }
            prints +=("\n");
        }

        //*********Matriz b y b inversa de las var basicas**********
        for(j=0;j<m;j++)
           for(i=n;i<n+m+nvara-nvarai;i++){
                if(j==i-n)
                    b[j][i-n]=mat[j][xb[j].posision-1];
                else
                    if(i-n < m){
                    b[j][i-n]=0;
                    }
           }
            //*****La primer matriz b inversa es la misma******
        for(j=0;j<m;j++)
           for(i=0;i<m;i++)
                binv[j][i]=b[j][i];

        //*******Matriz con los valores XBi********
        prints += "\n\nValores de B\n\n";
        for(j=0;j<m;j++){
            valb[j]=a[j][n];
            prints += "\t" +String.format("%.2f",valb[j]) + "\n";
        }
        
       salidaFinal.add(prints);
        prints = "";
        //************Empiezan las iteraciones**************
        //**************************************************
        condicion=0;
        do{
            acotada=0;
            //printf("\n***************FASE 1*******************");
            prints += "\n************Iteraccion"+(condicion+1 )+ "***************\n\n";

            //*******Impresion var basicas y no basicas******
            prints += "Variables basicas:\n";
            for(j=0;j<m;j++)
                prints += "\t +X" + xb[j].posision +"\n" ;
            prints+= "Variables no basicas:\n";
            for(j=0;j<n+nvara-nvarai;j++)
                prints += "\tX"+xnb[j].posision +"\n";

            //*******Impresion de las matrices b y b inv*****
            prints += "Matriz B:\n";
            for(j=0;j<m;j++){
                prints += "\t";
                for(i=0;i<m;i++)
                    prints += String.format("%.2f ",b[j][i]);
                prints += "\n";
            }
            prints += "Matriz B inversa:\n";
            for(j=0;j<m;j++){
                prints +="\t";
                for(i=0;i<m;i++)
                    prints += String.format("%.2f ",binv[j][i]);
                prints += "\n";
            }

            //*****Impresion de Z*******
            for(i=0;i<m;i++)
                    valbtemp[i]=0;
            for(j=0;j<m;j++){
                for(i=0;i<m;i++){
                    valbtemp[j]+=binv[j][i]*a[i][n];
                    //printf("  %.2f * %.2f  ",binv[j][i],a[i][n]);
                }
            }//****Calculo de la nueva xb****
            w=0;
            int t,ttt=0;
            t=xb[0].posision;
            for(i=0;i<n+nvare-1;i++){
                if(xnb[i].posision<xnb[i+1].posision){
                    xnb[i].posision=xnb[i+1].posision;
                    xnb[i+1].posision=t;
                    xnb[i].artificial=xnb[i+1].artificial;
                    xnb[i+1].artificial=ttt;
            }
            }
            prints+="\n";
            for(i=0;i<m;i++)
                valb[i]=valbtemp[i];
            //**Calculo de z**
            for(i=0;i<m;i++){
                w+=cw[xb[i].posision-1]*valb[i];
        //printf("W+=%.2f * %.2f",cw[xb[i].posision-1],valb[i]);
            }
            prints += "\nEl valor de W en la iteraccion"+ (condicion +1)+" es " + String.format("%.2f",w);

            //**********Variable de entrada*************
            for(j=0;j<m;j++)
                cbxbinv[j]=0;
            for(j=0;j<n+nvara-nvarai;j++)
                cbxbinxaj[j]=0;
            //fflush(stdin);
            for(j=0;j<m;j++)
                for(i=0;i<m;i++)
                    cbxbinv[j]+=cw[xb[i].posision-1]*binv[i][j];
            for(j=0;j<n+nvara-nvarai;j++)
                for(i=0;i<m;i++){
                    cbxbinxaj[j]+=cbxbinv[i]*mat[i][xnb[j].posision-1];
                    //printf(" cbxinvxaj+= %.2f = %.2f * %.2f ",cbxbinxaj[j],cbxbinv[i],mat[i][xnb[j].posision-1]);
                }
            //printf("\n\n");
            for(j=0;j<n+nvara-nvarai;j++){
                wj_cj[j]=cbxbinxaj[j]-cw[xnb[j].posision-1];
                //printf(" wj-cj= %.2f = %.2f - %.2f ",wj_cj[j],cbxbinxaj[j],cw[xnb[j].posision-1]);
            }
        prints += "\n\nWj-Cj = ";
        for(j=0;j<n+nvara-nvarai;j++)
        prints += String.format("%.2f ",wj_cj[j]);

        

            //********Condicion para seleccionar var de entrada********
            if(fo==1){//********Maximizar se busca el menor negativo**********
                varEtemp=zj_cj[0];
                for(i=0;i<n;i++){
                    if(zj_cj[i]<varEtemp && zj_cj[i]<0)
                        varEtemp=zj_cj[i];
                }
        //printf("\nvariable de entrada%.2f\n",varEtemp);
                if(varEtemp<0){
                    for(i=0;i<n;i++){
                        if(varEtemp==zj_cj[i]){
                            varE=i;
                            condicion++;
                            prints += "\nvariable de entrada"+ varE+"\n";
                            break;
                        }
                    }
                }
                else{
                    condicion=0;
                }
            }
            else{//*******Minimizar se busca el mayor positivo***********
                
                varEtemp=zj_cj[0];
                for(i=0;i<n+nvara-nvarai;i++){
                    if(wj_cj[i]>=varEtemp && wj_cj[i]>0){
                        
                        varEtemp=wj_cj[i];
                    }
                }
                if(varEtemp>0){
                    for(i=0;i<n;i++){
                        if(varEtemp==wj_cj[i]){
                            varE=i;
                            condicion++;
                            break;
                        }
                    }
                }
                
                else{
                    condicion=0;
                }
            }
            terminar=0;
                for(j=0;j<m;j++){
                    if(xb[j].artificial==1)
                        terminar++;
                }
                if(terminar==0){
                    if(w!=0)
                    {
                        acotada=2;
                        break;
                    }
                    condicion=0;
                }
            //***********Condicion para seleccionar variable de salida************
            varStemp=-1;
            if(condicion!=0){
                for(j=0;j<m;j++){
                    yai[j]=0;
                }
                for(j=0;j<m;j++){
                    for(i=0;i<m;i++){
                        yai[j]+= binv[j][i]*mat[i][xnb[varE].posision-1];
                        //printf("%.2f %d \n",mat[i][xnb[varE].posision-1],xnb[varE].posision);
                    }
                }
                for(j=0;j<m;j++)
                    e[j]=yai[j];
                for(i=0;i<m;i++){
                    if(yai[i]>0.01){
                        varStemp=((valb[i])/(yai[i]));
                        yai[i]=varStemp;
                    }
                    else{
                        yai[i]=-1;
                    }
                }
                for(i=0;i<m;i++){
                    if(yai[i]>0.01){
                        acotemp=yai[i];
                        break;
                    }
                    else
                        acotemp=-1;
                }
                if(varStemp==-1)//********Si no hay var de salida mayor a cero, entonces la sol no esta acotada*********
                {
                    acotada=1;
                    break;
                }
                for(i=0;i<m;i++){
                    prints += "y" + i + " = " + yai[i] + "\n";
                    if(yai[i]<varStemp&&yai[i]!=-1)
                        varStemp=yai[i];
                }
                if(varStemp>0){
                    for(i=0;i<m;i++){
                        if(varStemp==yai[i]){
                            varS=i;
                            break;
                        }
                    }
                }
                //printf("\n\nVariable de salida %.2f posision %d \n\n",varStemp,varS);


                //***********Actualizar Xb y Xnb*****************
                temp=xb[varS].posision;
                tempa=xb[varS].artificial;
                xb[varS].posision=xnb[varE].posision;
                xb[varS].artificial=xnb[varE].artificial;
                xnb[varE].posision=temp;
                xnb[varE].artificial=tempa;

                //****************Calcular las nuevas matrices b y b inversa********************
                for(i=0;i<m;i++){
                    for(j=0;j<m;j++){
                        b[j][i]=mat[j][xb[i].posision-1];
                    }
                }
                //*****b inversa*******
                tempe=e[varS];
                for(j=0;j<m;j++){
                    if(varS==j)
                        e[j]=1/tempe;
                    else
                        e[j]=-e[j]/tempe;
                }
                for(j=0;j<m;j++){
                    for(i=0;i<m;i++){
                        if(j==i)
                            E[j][i]=1;
                        else
                            E[j][i]=0;
                    }
                }
                for(j=0;j<m;j++){
                    for(i=0;i<m;i++){
                        if(varS==i)
                            E[j][i]=e[j];
                    }
                }
            prints += "\n\nMatriz E\n";
            for(j=0;j<m;j++){
                prints+= "\t";
                for(i=0;i<m;i++){
                    prints += String.format("%.2f ",E[j][i]);
                }
                prints+= "\n";
            }
            prints +="\n";
                for(j=0;j<m;j++){
                    for(i=0;i<m;i++){
                        binvtemp[j][i]=binv[j][i];
                        binv[j][i]=0;
                    }
                }
                for(j=0;j<m;j++){
                    for(k=0;k<m;k++){
                        for(i=0;i<m;i++){
                            binv[j][k]+=E[j][i]*binvtemp[i][k];
                        }
                    }
                }
                for(j=0;j<m;j++)
                    yai[j]=0;
            }
            salidaFinal.add(prints);
            prints = "";
        }while(condicion!=0);
        if(acotada==1){
            prints += "\n\n*********La solucion no esta acotada*********\n\n";
            
            salidaFinal.add(prints);
            prints ="";
        }else if(acotada==2){
            prints+="\n\n*********No existe una solucion*********\n\n";
            
            salidaFinal.add(prints);
            prints ="";
        }
        else{
        prints+= "\n\n**********************************\n************FASE 2****************\n**********************************\n\n";
        double mat2[][] = new double[m][n+m-nvarai];
        for(i=0;i<n+nvare;i++){
            for(k=0;k<nvare;k++){
                if(xnb[k].artificial==1){
                    for(j=0;j<n+nvare-1;j++){
                        xnb[j].posision=xnb[j+1].posision;
                        xnb[j].artificial=xnb[j+1].artificial;
                    }
                }
            }
        }
        double cbxbinxaj2[] = new double[n-nvarai],zj_cj2[] = new double[n-nvarai];

        //************Empiezan las iteraciones**************
        //**************************************************
        condicion=0;
        do{
            acotada=0;
            prints += "\n************Iteraccion "+ (condicion+1)+"***************\n\n";

            //*******Impresion var basicas y no basicas******
            prints+= "Variables basicas:\n";
            for(j=0;j<m;j++)
                prints += "\tX" + xb[j].posision + "\n";
            prints += "Variables no basicas:\n";
            for(j=0;j<n-nvarai;j++)
                prints += "\tX" +xnb[j].posision +"\n";

            //*******Impresion de las matrices b y b inv*****
            prints += "Matriz B:\n";
            for(j=0;j<m;j++){
                prints += "\t";
                for(i=0;i<m;i++)
                    prints +=  String.format("%.2f ",b[j][i]);
                prints += "\n";
            }
            prints += "Matriz B inversa:\n";
            for(j=0;j<m;j++){
                prints +="\t";
                for(i=0;i<m;i++)
                    prints += String.format("%.2f ",binv[j][i]);
                prints += "\n";
            }

            //*****Impresion de Z*******
            for(i=0;i<m;i++)
                    valbtemp[i]=0;
            for(j=0;j<m;j++){
                for(i=0;i<m;i++){
                    valbtemp[j]+=binv[j][i]*a[i][n];
                }
            }//****Calculo de la nueva xb****
            z=0;
            prints += "\n";
            for(i=0;i<m;i++)
                valb[i]=valbtemp[i];
            //**Calculo de z**
            for(i=0;i<m;i++){
                z+=cb[xb[i].posision-1]*valb[i];
            }
            prints+= "\nEl valor de Z en la iteraccion " + (condicion +1)+" es "+ String.format("%.2f",z);

            //**********Variable de entrada*************
            for(j=0;j<m;j++)
                cbxbinv[j]=0;
            for(j=0;j<n-nvarai;j++)
                cbxbinxaj2[j]=0;
            //fflush(stdin);
            for(j=0;j<m;j++)
                for(i=0;i<m;i++)
                    cbxbinv[j]+=cb[xb[i].posision-1]*binv[i][j];
            for(j=0;j<n-nvarai;j++)
                for(i=0;i<m;i++){
                    cbxbinxaj2[j]+=cbxbinv[i]*mat[i][xnb[j].posision-1];
                }
            for(j=0;j<n-nvarai;j++){
                zj_cj2[j]=  -1*(cbxbinxaj2[j]-cb[xnb[j].posision-1]);
            }
        prints += "\n\nZj-Cj = ";
        for(j=0;j<n-nvarai;j++)
        prints += String.format("%.2f ",zj_cj2[j]);

        //system("pause");
            fo=fotemp;

            //********Condicion para seleccionar var de entrada********
            if(fo==1){//********Maximizar se busca el menor negativo**********
                varEtemp=1;
                System.out.print("entro");
                for(i=0;i<n-nvarai;i++){
                    if(zj_cj2[i]<varEtemp && zj_cj2[i]<0)
                        varEtemp=zj_cj2[i];
                }
                System.out.print(varEtemp);
                if(varEtemp<0){
                    for(i=0;i<n;i++){
                        if(varEtemp==zj_cj2[i]){
                            varE=i;
                            condicion++;
                            break;
                        }
                    }
                }
                else{
                    condicion=0;
                    acotada = 1;
                    System.out.print("entro" + condicion);
                    
                }
            }
            else{//*******Minimizar se busca el mayor positivo***********
                varEtemp=0;
                for(i=0;i<n-nvarai;i++){
                    if(zj_cj2[i]>varEtemp && zj_cj2[i]<0)
                        varEtemp=zj_cj2[i];
                }
                if(varEtemp>0){
                    for(i=0;i<n;i++){
                        if(varEtemp==wj_cj[i]){
                            varE=i;
                            condicion++;
                            break;
                        }
                    }
                }
                else{
                    
                    condicion=0;
                    acotada = 1;
                }
            }

            //***********Condicion para seleccionar variable de salida************
            varStemp=-1;
            if(condicion!=0){
                for(j=0;j<m;j++){
                    yai[j]=0;
                }
                for(j=0;j<m;j++){
                    for(i=0;i<m;i++){
                        yai[j]+=binv[j][i]*mat[i][xnb[varE].posision-1];
                        //printf("%.2f %d \n",mat[i][xnb[varE].posision-1],xnb[varE].posision);
                    }
                }
                for(j=0;j<m;j++)
                    e[j]=yai[j];
                for(i=0;i<m;i++){
                    if(yai[i]>0.01){
                        varStemp=((valb[i])/(yai[i]));
                        yai[i]=varStemp;
                    }
                    else{
                        yai[i]=-1;
                    }
                }
                for(i=0;i<m;i++){
                    if(yai[i]>0.01){
                        acotemp=yai[i];
                        break;
                    }
                    else
                        acotemp=-1;
                }
                if(varStemp==-1)//********Si no hay var de salida mayor a cero, entonces la sol no esta acotada*********
                {
                    acotada=1;
                    break;
                }
                for(i=0;i<m;i++){
                    if(yai[i]<varStemp&&yai[i]!=-1)
                        varStemp=yai[i];
                }
                if(varStemp>0){
                    for(i=0;i<m;i++){
                        if(varStemp==yai[i]){
                            varS=i;
                            break;
                        }
                    }
                }
                //printf("\n\nVariable de salida %.2f posision %d \n\n",varStemp,varS);


                //***********Actualizar Xb y Xnb*****************
                temp=xb[varS].posision;
                tempa=xb[varS].artificial;
                xb[varS].posision=xnb[varE].posision;
                xb[varS].artificial=xnb[varE].artificial;
                xnb[varE].posision=temp;
                xnb[varE].artificial=tempa;

                //****************Calcular las nuevas matrices b y b inversa********************
                for(i=0;i<m;i++){
                    for(j=0;j<m;j++){
                        b[j][i]=mat[j][xb[i].posision-1];
                    }
                }
                //*****b inversa*******
                tempe=e[varS];
                for(j=0;j<m;j++){
                    if(varS==j)
                        e[j]=1/tempe;
                    else
                        e[j]=-e[j]/tempe;
                }
                for(j=0;j<m;j++){
                    for(i=0;i<m;i++){
                        if(j==i)
                            E[j][i]=1;
                        else
                            E[j][i]=0;
                    }
                }
                for(j=0;j<m;j++){
                    for(i=0;i<m;i++){
                        if(varS==i)
                            E[j][i]=e[j];
                    }
                }
            prints += "\n\nMatriz E\n";
            for(j=0;j<m;j++){
                prints += "\t";
                for(i=0;i<m;i++){
                    prints += String.format("%.2f ",E[j][i]);
                }
                prints += "\n";
            }
            prints += "\n";
                for(j=0;j<m;j++){
                    for(i=0;i<m;i++){
                        binvtemp[j][i]=binv[j][i];
                        binv[j][i]=0;
                    }
                }
                for(j=0;j<m;j++){
                    for(k=0;k<m;k++){
                        for(i=0;i<m;i++){
                            binv[j][k]+=E[j][i]*binvtemp[i][k];
                        }
                    }
                }
                for(j=0;j<m;j++)
                    yai[j]=0;
                
            }
            
            System.out.print("condicion" + condicion + "acotada" +acotada);
            salidaFinal.add(prints);
            prints = "";
        }while(condicion!=0);
        System.out.print("acotada" + acotada);
        if(acotada==1){
            prints += "\n\n*********La solucion no esta acotada*********\n\n";
            salidaFinal.add(prints);
            
        }
        else{
            prints += "\n\n*******Se llego a la solucion optima con**********\n\n";
            for(i=0;i<m;i++){
                prints += "X"+ xb[i].posision + " = " + String.format("%.2f\n",valb[i]);
            }
            for(i=0;i<n-nvarai;i++){
                prints+= "X" +xnb[i].posision + "= 0.00\n";
            }
            prints+= "\tCon Z = "+ String.format("%.2f",z);
            salidaFinal.add(prints);
        }

        }
    }
    }
}
