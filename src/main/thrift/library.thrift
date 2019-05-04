namespace java net.bolbat.snippets.serialization.thrift.library

struct Author {
    1: string id
    2: string name
}

struct Category {
    1: string id
    2: string name
}

struct Book {
    1: string id
    2: string name
    
    3: list<string> authors
    4: list<string> categories
}
