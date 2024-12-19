import { ReviewStatus } from './reviewstatus';

export class ReviewRequest {
  postId: string;
  status: ReviewStatus;
  description: string;

  constructor(postId: string, status: ReviewStatus, description: string) {
    this.postId = postId;
    this.status = status;
    this.description = description;
  }
}

export class PostRequest {
  authorId: number;
  title: string;
  content: string;
  inConcept: boolean;

  constructor(authorId: number, title: string, content: string, inConcept: boolean) {
    this.authorId = authorId;
    this.title = title;
    this.content = content;
    this.inConcept = inConcept;
  }
}

export class EditPostRequest {
  title: string;
  content: string;
  inConcept: boolean;

  constructor(title: string, content: string, inConcept: boolean) {
    this.title = title;
    this.content = content;
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

  constructor(
    id: number,
    authorId: number,
    title: string,
    content: string,
    author: string,
    dateCreated: string,
    inConcept: boolean,
    isApproved: boolean,
    inReview: boolean,
    rejectedReason: string
  ) {
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
