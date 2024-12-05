export class PostDTO {
  id: number;
  authorId: number;
  title: string;
  content: string;
  author: string;
  dateCreated: string;
  inConcept: boolean;
  isApproved: boolean;
  inReview: boolean;
  rejectedReason: string;

  constructor(id: number, authorId: number, title: string, content: string, author: string, dateCreated: string, inConcept: boolean, isApproved: boolean, inReview: boolean, rejectedReason: string) {
    this.id = id;
    this.authorId = authorId;
    this.title = title;
    this.content = content;
    this.author = author;
    this.dateCreated = dateCreated;
    this.inConcept = inConcept;
    this.isApproved = isApproved;
    this.inReview = inReview;
    this.rejectedReason = rejectedReason;
  }
}

export class NotificationDTO {
  content: string;

  constructor(content: string) {
    this.content = content;
  }
}