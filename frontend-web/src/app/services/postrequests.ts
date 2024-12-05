export class PostRequest {
  title: string;
  content: string;
  dateCreated: string;
  inConcept: boolean;

  constructor(title: string, content: string, dateCreated: string, inConcept: boolean) {
    this.title = title;
    this.content = content;
    this.dateCreated = dateCreated;
    this.inConcept = inConcept;
  }
}

export class EditPostRequest {
  title: string;
  content: string;
  dateCreated: string;
  inConcept: boolean;

  constructor(title: string, content: string, dateCreated: string, inConcept: boolean) {
    this.title = title;
    this.content = content;
    this.dateCreated = dateCreated;
    this.inConcept = inConcept;
  }
}

export class ApplyForReviewRequest {
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

export class AddNotificationRequest {
  authorId: number;
  content: string;

  constructor(authorId: number, content: string) {
    this.authorId = authorId;
    this.content = content;
  }
}

export class FilterPostsRequest {
  content: string;
  author: string;
  date: string;

  constructor(content: string, author: string, date: string) {
    this.content = content;
    this.author = author;
    this.date = date;
  }
}
