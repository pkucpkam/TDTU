#include <stdio.h>
#include <math.h>
#include <string.h>
#include <ctype.h>

int sobanbe(int n, int ld)
{
	int i,s1 = 0, s2 = 0;
	for (i = 1; i < n; i++)
	{
		if (n % i == 0)
		{
			s1 += i;
		}
	}
	for (i = 1; i < ld; i++)
	{
		if (ld % i == 0)
		{
			s2 += i;
		}
	}
	if ((s1 == ld) && (s2 == n))
		return 1;
	return 0;
}
int checkloi(int n,int ld, char w[])
{
	int dem = 0;
	if (n < 0 || n > 1000)
	{
		dem += 1;
	}
	if (ld < 1 || ld > 300)
	{
		dem += 1;
	}
	if (dem != 0)
		return 1;
	return 0;
}

void Wind(int n, int sc, float sg, int ld,FILE *f1)
{
	int bc,bg;
	float nd = 0.0;
	//th banh chung lon hon
	if ((sc >= sg) && (n >= sg))
	{
		bc = n/sc;
		bg = (n - bc*sc)/(sg);
		nd = n - bc*sc - sg*bg;

		if ((bc + bg) <= ld)
		{
			fprintf(f1,"%d %d %.3f",bc,bg,nd);
		}
		else if ((bc < ld) && (ld <(bc + bg)))
		{
			bg = ld - bc;
			nd = n - bc*sc - sg*bg;
			fprintf(f1,"%d %d %.3f",bc,bg,nd);
		}
		else
		{
			bc = ld;
			bg = 0;
			nd = n - bc*sc;
			fprintf(f1,"%d %d %.3f",bc,bg,nd);
		}
	}
	//banh giay lon hon
	else if ((sg > sc) && (n >= sc))
	{
		float min = 1000.0;
		int bg1,bc1,i;
		bc1 = n/sc;
		float nd1;
		for ( i = bc1; i >=0 ; i--)
		{
				bg1 = (n - i*sc)/sg;
				nd1 = n - i*sc - bg1*sg;
				if (nd1 < min)
				{
					bg = bg1;
					bc = i;	
					min = nd1;
				}
		}
		nd = n - sc*bc - sg*bg;
		if (bc + bg <= ld)
		{
			fprintf(f1,"%d %d %.3f", bc,bg,nd);
		}
		else
		{
			min = 1000.0;
			for (i = 0; i <= ld; i++)
			{
				bg1 = (n - sc*i)/sg;
				if (bg1 + i > ld)
				{
					bg1 = ld - i;
				}
				nd1 = n - i*sc - bg1*sg;
				if ((nd1 < min) && (nd1 > 0))
				{
					bc = i;
					bg = bg1;
					min = nd1;
				}
			}
			nd = n - sc*bc - sg*bg;
			fprintf(f1,"%d %d %.3f", bc,bg,nd);
		}
	}
	else 
	{
		nd = n*1.0;
		fprintf(f1,"%d %d %.3f",0 ,0, nd);
	}
}

void Rain(int n,int sc, float sg,int ld,FILE *f1)
{
	float n1 = n*1.0;
	int dem = 0,bg = 0,bc = 0,i;
	if (sc >= sg)
	{
		while (( n1 >= sg ) && ((bc + bg) < ld))
		{
			if ((n1 >= sc) && ((bc + bg) < ld))
			{
			    n1 -= sc;
			    bc += 1;
			}
			if ((n1 >= sg) && ((bc + bg) < ld)) 
			{
			   	n1 -= sg;
		        bg += 1;
		    }
		}
		fprintf(f1,"%d %d %.3f",bc,bg,n1);
	}
	else 
	{
		while ((n1 >= sc) && ((bc + bg) < ld))
		{
			if ((n1 >= sg) && ((bc + bg) < ld))
		    {
		    	n1 -= sg;
		        bg += 1;
		    }
		    if ((n1 >= sc) && ((bc + bg) < ld))
		    {
		        n1 -= sc;
		        bc += 1;
		    }
		}
		fprintf(f1,"%d %d %.3f",bc,bg,n1);
	}
}


void Fog(int n, int dc,int dg,FILE *f1)
{
	float nd = 1.0*n;
	fprintf(f1,"%d %d %.3f", dc,dg,nd);
}

void Cloud(int n,int sc,float sg,int ld,FILE *f1)
{
	if (sobanbe(n,ld) == 1)
	{
		float nd = 1.0*n;
		fprintf(f1,"%d %d %.3f",0,0,nd);
	}
	else
	{
        int bc,bg;
		float nd = 0.0;
		//th giay lon hon
		if ((sg >= sc) && (n > sc))
		{
			bg = n/sg;
			bc = (n - bg*sg)/(sc);
			nd = n - bc*sc - sg*bg;
	
			if ((bc + bg) <= ld)
			{
				fprintf(f1,"%d %d %.3f",bc,bg,nd);
			}
			else if ((bg < ld) && (ld <(bc + bg)))
			{
				bc = ld - bg;
				nd = n - bc*sc - sg*bg;
				fprintf(f1,"%d %d %.3f",bc,bg,nd);
			}
			else
			{
				bg = ld;
				bc = 0;
				nd = n - bg*sg;
				fprintf(f1,"%d %d %.3f",bc,bg,nd);
			}
		}
		//banh chung lon hon
		else if ((sg < sc) && (n >= sg))
		{
			float min = 1000.0;
			int bg1,bc1,i;
			bg1 = n/sg;
			float nd1;
			for ( i = bg1; i >=0 ; i--)
			{
				bc1 = (n - i*sg)/sc;
				nd1 = n - i*sg - bc1*sc;
				if (nd1 < min)
				{
					bc = bc1;
					bg = i;	
					min = nd1;
				}
			}
			nd = n - sc*bc - sg*bg;
			if (bc + bg <= ld)
			{
				fprintf(f1,"%d %d %.3f", bc,bg,nd);
			}
			else
			{
				float min = 1000.0;
				for (i = 0; i <= ld; i++)
				{
					bc1 = (n - sg*i)/sc;
					if ( bc1 + i > ld)
					{
						bc1 = ld - i;	
					}
					nd1 = n - i*sg - bc1*sc; 
					if ((nd1 < min) && (nd1 > 0))
					{
						bg = i;
						bc = bc1;
						min = nd1;
					}
				}
				nd = n - sc*bc - sg*bg;
				fprintf(f1,"%d %d %.3f", bc,bg,nd);
			}
		}
		else 
		{
			nd = n*1.0;
			fprintf(f1,"%d %d %.3f",0 ,0, nd);
		}
	}
}
int main()
{
	int n,dc,dg,ld;
	char w[100];
	FILE *f,*f1;
// doc file
	f = fopen("input.inp", "r");
	f1 = fopen("output.out","w+");
	fscanf(f,"%d",&n);
	fscanf(f,"%d",&dc);
	fscanf(f,"%d",&dg);
	fscanf(f,"%d",&ld);
	fscanf(f,"%s",&w);
	
// main
	float pi =3.1415926535;
	//cong thuc tinh
	int sc = (pow(dc,2));
    float sg = (pow(dg,2)*pi/4);
	if (checkloi(n,ld,w) == 1)
	{
		fprintf(f1,"%d %d %d", -1, -1, n);
	}
    else if ((dc <=0) && (dg >0) && (strcmp(w,"Fog") != 0))
    {
        int bg = n/sg;
        float nd = n - bg*sg;
        fprintf(f1,"%d %d %.3f",0,bg,nd);
    }
    else if ((dg <= 0) && (dc > 0) && (strcmp(w,"Fog") != 0)) 
    {
        int bc = n/sc;
        float nd = n - bc*sc;
        fprintf(f1,"%d %d %.3f", bc,0,nd);
    }
    else if ((dg <= 0) && (dc <=0) && (strcmp(w,"Fog") != 0))
    {
    	float nd = n*1.0;
	    fprintf(f1,"%d %d %.3f",0 ,0,nd);	
    }
	else 
	{
		if (strcmp(w,"Wind") == 0)
		{
			Wind(n,sc,sg,ld,f1);
		}
		else if (strcmp(w,"Rain") == 0)
		{
			Rain(n,sc,sg,ld,f1);
		}
		else if (strcmp(w,"Cloud") == 0)
		{
			Cloud(n,sc,sg,ld,f1);
		}
		else if (strcmp(w,"Fog") == 0)
		{
			Fog(n,dc,dg,f1);
		}
		else 
		{
			int g = dc % 6;
			int h = ld % 5;
			//bang dieu kien
			int a[5][6];
			a[0][0] = 5; a[1][1] = 5; a[2][2] = 5; a[3][3] = 5; a[4][4] = 5;
			a[0][1] = 7; a[1][2] = 7; a[2][3] = 7; a[3][4] = 7; a[4][5] = 7;
			a[0][2] = 10; a[1][3] = 10; a[2][4] = 10; a[3][5] = 10; a[4][0] = 10;
			a[0][3] = 12; a[1][4] = 12; a[2][5] = 12; a[3][0] = 12; a[4][1] = 12;
			a[0][4] = 15; a[1][5] = 15; a[2][0] = 15; a[3][1] = 15; a[4][2] = 15;
			a[0][5] = 20; a[1][0] = 20; a[2][1] = 20; a[3][2] = 20; a[4][3] = 20;

			int x = a[h][g];
			n = n + n*x/100;
			ld = ld - x;
			if (ld <= 0)
			{
				fprintf(f1,"%d %d %.3f",0 ,0 ,1.0*n);
			}
			else if ((dc + dg) % 3 == 0)
			{
				Rain(n,sc,sg,ld,f1);
			}		
			else if ((dc + dg) % 3 == 1)
			{
				Wind(n,sc,sg,ld,f1);
			}
			else
			{
				Cloud(n,sc,sg,ld,f1);
			}
		}
	}
	fclose(f);
	fclose(f1);
	return 0;
}
