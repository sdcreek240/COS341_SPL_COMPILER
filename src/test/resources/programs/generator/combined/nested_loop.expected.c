float vN0;
vN0 = 0;
v1 = vN0;
L0: /* until */
float vN1;
vN1 = v1;
float vN2;
vN2 = 2;
if ( vN1 < vN2 ) goto L2;
goto L1;
L2: /* do */
float vN3;
vN3 = 0;
v2 = vN3;
L3: /* do */
float vN4;
float vN5;
vN5 = v2;
float vN6;
vN6 = 1;
vN4 = vN5 + vN6;
v2 = vN4;
/* end */
/* while */
float vN7;
vN7 = v2;
float vN8;
vN8 = 2;
if ( vN7 < vN8 ) goto L3;
L4: /* exit */
float vN9;
float vN10;
vN10 = v1;
float vN11;
vN11 = 1;
vN9 = vN10 + vN11;
v1 = vN9;
/* end */
goto L0;
L1:
/* exit */
/* end */