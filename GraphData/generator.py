import random as r

base = "../Caso2/data/pruebas/referencias_"

for i in [16,32,64,128]:
    f = open(base+str(i)+"_1.txt","w+")
    f.write("1\n")
    f.write(str(i)+"\n")
    f.write(str(500)+"\n")
    for j in range(500):
        ref = r.randint(0,i-1)
        bit = "r" if r.randint(0,1) == 0 else "m"
        f.write(("{},{}\n").format(ref,bit))
    for j in range(2,i+1):
        f = open(base+str(i)+"_1.txt","r")
        f_temp = open(base+str(i)+"_"+str(j)+".txt", "w")
        f.readline()
        f_temp.write(str(j)+"\n")
        f_temp.write(f.read())
        f_temp.close()
    f.close()