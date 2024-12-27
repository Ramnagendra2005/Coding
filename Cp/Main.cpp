#include<bits/stdc++.h>
#define int long long
using namespace std;
signed main(){
   int t;
   cin>>t;
   while(t--){
     int a,b,c;
    cin>>a>>b>>c;
    if((a==(b+c)) || (b==(a+c))|| (c==(b+a))){
        cout<<"YES"<<"\n";
    } else{
        cout<<"NO"<<endl;
    }
   }
}