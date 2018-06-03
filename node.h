typedef struct node {
    char name;
    struct node *father;
    struct node *mother;
} node;

node* findNode(char character, node* head) {
    node* result = NULL;
    
    if(head->name == character) {
        return head;
    }
    
    if(head->father != NULL) {  //head의 father에 무언가가 있을 때
        result = findNode(character, head->father);
        if(result != NULL) {
            return result;
        }
    }
    
    if(head->mother != NULL) {  //head의 mother에 무언가가 있을 때
        result = findNode(character, head->mother);
        if(result != NULL) {
            return result;
        }
    }
    return NULL;
}

node* printRelation(char character, node* head, char* stack, int* size) {
    stackPush(stack, size, head->name);
    node* result = NULL;
    
    if(head->name == character) {
        stackPrint(stack, size);
        return head;
    }
    
    if(head->father != NULL) {  //head의 father에 무언가가 있을 때
        stackPush(stack, size, 'F');
        result = printRelation(character, head->father, stack, size);
        if(result != NULL) {
            return result;
        }
    }
    
    if(head->mother != NULL) {  //head의 mother에 무언가가 있을 때
        stackPush(stack, size, 'M');
        result = printRelation(character, head->mother, stack, size);
        if(result != NULL) {
            return result;
        }
    }
    stackPop(stack, size);
    stackPop(stack, size);
    return NULL;
}

node* initNode(char name, node* father, node* mother) {
    node* result = (node*) malloc(sizeof(node));    
    result->name = name;
    result->father = father;
    result->mother = mother;
    return result;
}

void traversal(node* head) {
    printf("%c", head->name);
    if(head->father != NULL) {
        traversal(head->father);
    }
    if(head->mother != NULL) {
        traversal(head->mother);
    }
}

void printTree(node* head) {
    traversal(head);
    printf("\n");
}
