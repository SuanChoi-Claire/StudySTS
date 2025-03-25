# main.py
from fastapi import FastAPI,form,UploadFile, File
from fastapi.middleware.cors import CORSMiddleware

app = FastAPI()

# CORS 설정
app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:3000"],  # React 애플리케이션 도메인
    allow_credentials=True,
    allow_methods=["*"],  # 모든 HTTP 메서드 허용 (GET, POST, PUT 등)
    allow_headers=["*"],  # 모든 헤더 허용
)




# 2. Get  요청: 전체 게시물조회 API
@app.get("/board")
def getBoards():
    content=[{"writer":"Writer name","id":1,"title":"title test ","content":"test content1 "},{"writer":"Writer name2","id":2,"title":"title test2","content":"test content2"}]
    return {"content": content , "totalPages": 100}


     
    
    
# 1. GET 요청: 게시물 상세페이지 조회하기  API
@app.get("/board/{id}")
def getBoardById(id: int):
    content ={
    "createdAt": "createAt 2025/05/052 이거파이썬",
    "writer": "write test",
    "id": id,
    "title": "title test2",
    "content": "content test",
    "views": 13
}
    
    return content
    
    #3. post요청 게시물 등록페이지  API
    @app.post("/board")    
 async def createBoard(writer: str = Form(...),title: str = Form(...),content: str = Form(...),file: UploadFile = File(...)):
     fileBody = await file.read()
     result = {"writer":writer,"Title":title, "file Size": len(fileBody) "file" : file.filename}
     return result 



# 3. PUT 요청: 기존 아이템을 수정하는 API
@app.put("/items/{item_id}")
def update_item(item_id: int, name: str):
    return {"item_id": item_id, "name": name}

# 4. DELETE 요청: 특정 아이템을 삭제하는 API
@app.delete("/items/{item_id}")
def delete_item(item_id: int):
    return {"message": f"Item {item_id} deleted"}