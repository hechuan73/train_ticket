package main

import (
	"github.com/hoisie/web"
	//"labix.org/v2/mgo"
	//"labix.org/v2/mgo/bson"
	//"fmt"
)

//const (
//	MONGODB_URL = "ts-news-mongo:27017"
//)

//func getNews(val string) string {
//
//	//创建连接
//	session, err := mgo.Dial(MONGODB_URL)
//	if err != nil {
//		panic(err)
//	}
//	defer session.Close()
//
//	session.SetMode(mgo.Monotonic, true)
//	// db := session.DB("xtest")   //数据库名称
//	// collection := db.C("xtest") // 集合名称
//	c := session.DB("xtest").C("xtest")
//
//	 //查询多条数据 Find().All()
//	 var personAll []News
//	 err = c.Find(nil).All(&personAll)
//	 for i := 0; i < len(personAll); i++ {
//	 	fmt.Println("Person ", personAll[i].Name, personAll[i].Phone)
//	 }
//
//}

type News struct {
	Title   string `bson:"Title"`
	Content string `bson:"Content"`
}

func hello(val string) string {
	var str = []byte(`[
                       {"Title": "News Service Complete", "Content": "Congratulations:Your News Service Complete"},
                       {"Title": "Total Ticket System Complete", "Content": "Just a total test"}
                    ]`)
	return string(str)
}

func main() {
	web.Get("/(.*)", hello)
	web.Run("0.0.0.0:12862")
}