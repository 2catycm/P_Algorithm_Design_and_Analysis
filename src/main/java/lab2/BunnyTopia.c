#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <assert.h>
int size_tgreatercmp(const void* a, const void* b){
    size_t arg1 = *(const size_t*)a;
    size_t arg2 = *(const size_t*)b;
    if (arg1 < arg2) return 1;
    if (arg1 > arg2) return -1;
    return 0;
}
int main(){
    int n, m;
    scanf("%d %d", &n, &m);
    size_t reward[n];
    for(int i = 0; i < n; i++){
        scanf("%zu", &reward[i]);
        reward[i]<<=1;
    }
    for (int i = 0; i < m; i++)
    {
        int u,v,w;
        scanf("%d %d %d", &u, &v, &w);
        u--; v--;
        reward[u]+=w;
        reward[v]+=w;
    }
    qsort(reward, n, 
    sizeof(size_t), size_tgreatercmp);
    int64_t P = 0;
    for(int i = 0; i < n; i+=2){
        if (i+1<n){
            P+=reward[i];
            P-=reward[i+1];
        }
        else
            P+=reward[i];
    }
    assert(P%2==0);
    printf("%lld\n", P>>1);
    return 0;
}