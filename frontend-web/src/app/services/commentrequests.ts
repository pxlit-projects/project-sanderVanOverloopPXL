export class AddCommentRequest {
  message: string;
  postId: number;

  constructor(message: string, postId: number) {
    this.message = message;
    this.postId = postId;
  }
}

export class EditCommentRequest {
  message: string;

  constructor(message: string) {
    this.message = message;
  }
}
