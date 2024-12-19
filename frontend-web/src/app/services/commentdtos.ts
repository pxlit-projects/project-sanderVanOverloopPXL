export class CommentDTO {
  id: number;
  postId: number;
  message: string;
  usernameMadeBy: string;
  dateCreated: string;

  constructor(id: number, postId: number, message: string, usernameMadeBy: string, dateCreated: string) {
    this.id = id;
    this.postId = postId;
    this.message = message;
    this.usernameMadeBy = usernameMadeBy;
    this.dateCreated = dateCreated;
  }
}
