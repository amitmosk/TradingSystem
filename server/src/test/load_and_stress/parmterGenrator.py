def register():
    file=open("registerParmas.csv","w+")
    file.write("email,pw,name,lastName,birth_date,session_id\n")
    for i in range(100000):
        file.write(f"test{i}@gmail.com,12345678aA,amit,grumet,2000-06-11,{i}\n")
    file.close()


def login():
    file=open("loginParmas.csv","w+")
    file.write("email,password,session_id\n")
    for i in range(1000):
        file.write(f"test{i}@gmail.com,12345678aA,{i}\n")
    file.close()

def open_store():
    file = open("open_storeParmas.csv", "w+")
    file.write("store_name,session_id\n")
    for i in range(1000):
        file.write(f"store{i},{i}\n")
    file.close()

def add_product():
    file = open("add_productParmas.csv", "w+")
    file.write("store_id,quantity,name,price,category,key_words,session_id\n")
    for i in range(1000):
        for j in range(1000):
            file.write(f"{i+2},{10000},dog+{j},100,dogs,dogs,{i}\n")
    file.close()

def add_to_cart():
    file = open("add_to_cartParams.csv", "w+")
    file.write("store_id,product_id,quantity,session_id\n")
    for i in range(1000):
        file.write(f"{i+2},{i+3},1,{i}\n")
    file.close()

def buy_cart():
    file = open("buy_cartParams.csv", "w+")
    file.write("paymentInfo,supplyInfo,session_id\n")
    for i in range(1000):
        file.write(f"test,test,{i}\n")
    file.close()

if __name__ == '__main__':
    register()
    login()
    add_product()
    open_store()
    add_to_cart()
    buy_cart()
